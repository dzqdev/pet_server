package com.sise.pet.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.sise.pet.dto.SysMenuDto;
import com.sise.pet.entity.SysMenu;
import com.sise.pet.entity.SysRole;
import com.sise.pet.mapper.SysMenuMapper;
import com.sise.pet.service.ISysMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sise.pet.vo.MenuMetaVo;
import com.sise.pet.vo.MenuVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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

    @Override
    public List<SysMenu> getMenus(Long pid) {

        return null;
    }

    @Override
    public List<SysMenuDto> findByRoles(List<SysRole> roles) {
        Set<Long> roleIds = roles.stream().map(SysRole::getId).collect(Collectors.toSet());
        List<SysMenu> menuList = menuMapper.findByRoles(roleIds);
        List<SysMenuDto> list = menuList.stream().map(menu -> convert(menu)).collect(Collectors.toList());
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
    public List<MenuVo> buildMenus(List<SysMenuDto> menuDTOs) {
        List<MenuVo> list = new LinkedList<>();
        menuDTOs.forEach(menuDTO -> {
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
                            MenuVo nonChildrenMenuVo = new MenuVo();
                            nonChildrenMenuVo.setMeta(menuVo.getMeta());
                            // 非外链
                            if (!menuDTO.getIFrame()) {
                                nonChildrenMenuVo.setPath("index");
                                nonChildrenMenuVo.setName(menuVo.getName());
                                nonChildrenMenuVo.setComponent(menuVo.getComponent());
                            } else {
                                nonChildrenMenuVo.setPath(menuDTO.getPath());
                            }
                            menuVo.setName(null);
                            menuVo.setMeta(null);
                            menuVo.setComponent("Layout");
                            List<MenuVo> children = new ArrayList<>();
                            children.add(nonChildrenMenuVo);
                            menuVo.setChildren(children);
                        }
                        list.add(menuVo);
                    }
                }
        );
        return list;
    }

    public SysMenuDto convert(SysMenu menu){
        SysMenuDto sysMenuDto = new SysMenuDto();
        BeanUtils.copyProperties(menu, sysMenuDto);
        return sysMenuDto;
    }

}
