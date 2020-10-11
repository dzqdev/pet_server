package com.sise.pet.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sise.pet.dto.SysMenuDto;
import com.sise.pet.dto.convert.SysMenuConvert;
import com.sise.pet.dto.query.SysMenuQueryCriteria;
import com.sise.pet.entity.SysMenu;
import com.sise.pet.entity.SysRole;
import com.sise.pet.exception.Asserts;
import com.sise.pet.mapper.SysMenuMapper;
import com.sise.pet.service.ISysMenuService;
import com.sise.pet.service.ISysRoleService;
import com.sise.pet.vo.MenuMetaVo;
import com.sise.pet.vo.MenuVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 系统菜单 服务实现类
 * </p>
 *
 * @author author
 * @since 2020-09-07
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {

    @Resource
    private SysMenuMapper menuMapper;

    @Resource
    private SysMenuConvert sysMenuConvert;

    @Resource
    private ISysRoleService roleService;

    @Override
    public List<SysMenuDto> getMenus(Long pid) {
        LambdaQueryWrapper<SysMenu> queryWrapper = new LambdaQueryWrapper<>();
        if (Objects.isNull(pid) || pid.equals(0L)) {
            queryWrapper.isNull(SysMenu::getPid);
        } else {
            queryWrapper.eq(SysMenu::getPid, pid);
        }
        List<SysMenu> menuList = list(queryWrapper);
        return sysMenuConvert.toDto(menuList);
    }

    @Override
    public List<SysMenuDto> findByRoles(List<SysRole> roles, Integer type) {
        Set<Long> roleIds = roles.stream().map(SysRole::getId).collect(Collectors.toSet());
        List<SysMenu> menuList = menuMapper.findByRoles(roleIds, type);
        List<SysMenuDto> list = menuList.stream().map(menu -> sysMenuConvert.toDto(menu)).collect(Collectors.toList());
        return list;
    }

    @Override
    public List<SysMenuDto> buildTree(List<SysMenuDto> menuDTOs) {
        List<SysMenuDto> trees = new ArrayList<>();
        Set<Long> ids = new HashSet<>();
        for (SysMenuDto menuDTO : menuDTOs) {
            if (menuDTO.getPid() == null) {
                trees.add(menuDTO);
            }
            for (SysMenuDto it : menuDTOs) {
                if (menuDTO.getId().equals(it.getPid())) {
                    if (menuDTO.getChildren() == null) {
                        menuDTO.setChildren(new ArrayList<>());
                    }
                    menuDTO.getChildren().add(it);
                    ids.add(it.getId());
                }
            }
        }
        if (trees.size() == 0) {
            trees = menuDTOs.stream().filter(s -> !ids.contains(s.getId())).collect(Collectors.toList());
        }
        return trees;
    }

    @Override
    public List<MenuVo> buildMenus(List<SysMenuDto> menuDtos) {
        List<MenuVo> list = new LinkedList<>();
        menuDtos.forEach(menuDTO -> {
                    if (menuDTO != null) {
                        List<SysMenuDto> menuDtoList = menuDTO.getChildren();
                        MenuVo menuVo = new MenuVo();
                        menuVo.setName(ObjectUtil.isNotEmpty(menuDTO.getComponentName()) ? menuDTO.getComponentName() : menuDTO.getTitle());
                        // 一级目录需要加斜杠，不然会报警告
                        menuVo.setPath(menuDTO.getPid() == null ? "/" + menuDTO.getPath() : menuDTO.getPath());
                        menuVo.setHidden(menuDTO.getHidden());
                        // 如果不是外链
                        if (!menuDTO.getIFrame()) {
                            if (menuDTO.getPid() == null) {
                                menuVo.setComponent(StrUtil.isEmpty(menuDTO.getComponent()) ? "Layout" : menuDTO.getComponent());
                            } else if (!StrUtil.isEmpty(menuDTO.getComponent())) {
                                menuVo.setComponent(menuDTO.getComponent());
                            }
                        }
                        menuVo.setMeta(new MenuMetaVo(menuDTO.getTitle(), menuDTO.getIcon(), !menuDTO.getCache()));
                        if (menuDtoList != null && menuDtoList.size() != 0) {
                            menuVo.setAlwaysShow(true);
                            menuVo.setRedirect("noredirect");
                            menuVo.setChildren(buildMenus(menuDtoList));
                            // 处理是一级菜单并且没有子菜单的情况
                        } else if (menuDTO.getPid() == null) {
                            MenuVo menuVo1 = new MenuVo();
                            menuVo1.setMeta(menuVo.getMeta());
                            // 非外链
                            if (!menuDTO.getIFrame()) {
                                menuVo1.setPath("index");
                                menuVo1.setName(menuVo.getName());
                                menuVo1.setComponent(menuVo.getComponent());
                            } else {
                                menuVo1.setPath(menuDTO.getPath());
                            }
                            menuVo.setName(null);
                            menuVo.setMeta(null);
                            menuVo.setComponent("Layout");
                            List<MenuVo> list1 = new ArrayList<>();
                            list1.add(menuVo1);
                            menuVo.setChildren(list1);
                        }
                        list.add(menuVo);
                    }
                }
        );
        return list;
    }

    @Override
    public List<SysMenuDto> queryAll(SysMenuQueryCriteria queryCriteria) {
        LambdaQueryWrapper<SysMenu> queryWrapper = new LambdaQueryWrapper<>();
        Long pid = queryCriteria.getPid();
        if (pid == null) {
            queryWrapper.isNull(SysMenu::getPid);
        } else {
            queryWrapper.eq(SysMenu::getPid, queryCriteria.getPid());
        }
        String blurry = queryCriteria.getBlurry();
        List<Timestamp> createTime = queryCriteria.getCreateTime();
        if (StrUtil.isNotBlank(blurry)) {
            queryWrapper.like(SysMenu::getTitle, blurry)
                    .or().like(SysMenu::getPermission, blurry)
                    .or().like(SysMenu::getComponent, blurry);
        }

        if (CollectionUtil.isNotEmpty(createTime)) {
            queryWrapper.ge(SysMenu::getCreateTime, createTime.get(0))
                    .le(SysMenu::getCreateTime, createTime.get(1));
        }
        List<SysMenu> menus = list(queryWrapper);
        return sysMenuConvert.toDto(menus);
    }

    @Override
    public void create(SysMenu sysMenu) {
        if (sysMenu.getPid().equals(0L)) {
            sysMenu.setPid(null);
        }
        if (sysMenu.getIFrame()) {
            String http = "http://", https = "https://";
            if (!(sysMenu.getPath().toLowerCase().startsWith(http) || sysMenu.getPath().toLowerCase().startsWith(https))) {
                Asserts.fail("外链必须以http://或者https://开头");
            }
        }
        save(sysMenu);
        updateSubCount(sysMenu.getPid());
    }

    @Override
    public Set<SysMenu> getDeleteMenus(List<SysMenu> menuList, Set<SysMenu> menuSet) {
        for (SysMenu menu : menuList) {
            menuSet.add(menu);
            List<SysMenu> list = list(new LambdaQueryWrapper<SysMenu>().eq(SysMenu::getPid, menu.getId()));
            if (CollectionUtil.isNotEmpty(list)) {
                getDeleteMenus(list, menuSet);
            }
        }
        return menuSet;
    }

    @Override
    public void delete(Set<SysMenu> menuSet) {
        for (SysMenu menu : menuSet) {
            roleService.untiedMenu(menu.getId());
            removeById(menu.getId());
            updateSubCount(menu.getPid());
        }
    }

    @Override
    public void update(SysMenu resources) {
        SysMenu oldMenu = getById(resources.getId());
        Long oldPid = oldMenu.getPid();
        Long newPid = resources.getPid();
        oldMenu.setTitle(resources.getTitle());
        oldMenu.setComponent(resources.getComponent());
        oldMenu.setPath(resources.getPath());
        oldMenu.setIcon(resources.getIcon());
        oldMenu.setIFrame(resources.getIFrame());
        oldMenu.setPid(resources.getPid());
        oldMenu.setMenuSort(resources.getMenuSort());
        oldMenu.setCache(resources.getCache());
        oldMenu.setHidden(resources.getHidden());
        oldMenu.setPermission(resources.getPermission());
        oldMenu.setType(resources.getType());
        update(oldMenu);
        // 计算父级菜单节点数目
        updateSubCount(oldPid);
        updateSubCount(newPid);
    }

    @Override
    public List<SysMenuDto> getSuperior(SysMenuDto menuDto, List<SysMenu> menus) {
        if (menuDto.getPid() == null) {
            menus.addAll(list(new LambdaQueryWrapper<SysMenu>().isNull(SysMenu::getPid)));
            return sysMenuConvert.toDto(menus);
        }
        menus.addAll(list(new LambdaQueryWrapper<SysMenu>().eq(SysMenu::getPid, menuDto.getPid())));
        SysMenu menu = getById(menuDto.getPid());
        return getSuperior(sysMenuConvert.toDto(menu), menus);
    }

    /**
     * 更新父节点菜单数目
     *
     * @param pid
     */
    private void updateSubCount(Long pid) {
        if (pid != null) {
            int count = count(new LambdaQueryWrapper<SysMenu>().eq(SysMenu::getPid, pid));
            update(new LambdaUpdateWrapper<SysMenu>().eq(SysMenu::getId, pid).set(SysMenu::getSubCount, count));
        }
    }

}
