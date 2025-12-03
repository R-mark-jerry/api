import { createRouter, createWebHistory } from 'vue-router'
import Layout from '@/layout'

/**
 * Note: 路由配置项
 *
 * hidden: true                     // 当设置 true 的时候该路由不会再侧边栏出现 如401，login等页面，或者如一些编辑页面/edit/1
 * alwaysShow: true                 // 当你一个路由下面的 children 声明的路由大于1个时，自动会变成嵌套的模式--如组件页面
 *                                // 只有一个时，会将那个子路由当做根路由显示在侧边栏--如引导页面
 *                                // 若你想不管路由下面的 children 声明的个数都显示你的根路由
 *                                // 你可以设置 alwaysShow: true，这样它就会忽略之前定义的规则，一直显示根路由
 * redirect: noRedirect           // 当设置 noRedirect 的时候该路由在面包屑导航中不可被点击
 * name:'router-name'             // 设定路由的名字，一定要填写不然使用<keep-alive>时会出现各种问题
 * meta : {
    roles: ['admin','editor']    // 设置该路由进入的权限，支持多个权限叠加通过逗号分隔
    title: 'title'               // 设置该路由在侧边栏和面包屑中展示的名字
    icon: 'svg-name'             // 设置该路由的图标，对应路径src/assets/icons/svg
    breadcrumb: false            // 如果设置为false，则不会在breadcrumb面包屑中显示
    activeMenu: '/system/user'   // 当路由设置了该属性，则会高亮相对应的侧边栏。
  }
 */

// 公共路由
export const constantRoutes = [
  {
    path: '/redirect',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/redirect/:path(.*)',
        component: () => import('@/views/redirect/index')
      }
    ]
  },
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },
  {
    path: '/register',
    component: () => import('@/views/register'),
    hidden: true
  },
  {
    path: "/:pathMatch(.*)*",
    component: () => import('@/views/error/404'),
    hidden: true
  },
  {
    path: '/401',
    component: () => import('@/views/error/401'),
    hidden: true
  }
]

// 动态路由，基于用户权限动态去加载
export const dynamicRoutes = [
  {
    path: '',
    component: Layout,
    redirect: 'index',
    children: [
      {
        path: 'index',
        component: () => import('@/views/index/index'),
        name: 'Index',
        meta: { title: '首页', icon: 'dashboard', affix: true }
      }
    ]
  },
  {
    path: '/api',
    component: Layout,
    redirect: 'noRedirect',
    name: 'Api',
    meta: {
      title: 'API管理',
      icon: 'api'
    },
    children: [
      {
        path: 'group',
        component: () => import('@/views/api/group/index'),
        name: 'ApiGroup',
        meta: { title: 'API分组', icon: 'tree' }
      },
      {
        path: 'info',
        component: () => import('@/views/api/info/index'),
        name: 'ApiInfo',
        meta: { title: 'API信息', icon: 'list' }
      },
      {
        path: 'param',
        component: () => import('@/views/api/param/index'),
        name: 'ApiParam',
        meta: { title: 'API参数', icon: 'parameter' }
      },
      {
        path: 'permission',
        component: () => import('@/views/api/permission/index'),
        name: 'ApiPermission',
        meta: { title: 'API权限', icon: 'lock' }
      },
      {
        path: 'log',
        component: () => import('@/views/api/log/index'),
        name: 'ApiLog',
        meta: { title: '调用日志', icon: 'log' }
      }
    ]
  },
  // 系统管理
  {
    path: '/system',
    component: Layout,
    name: 'System',
    meta: {
      title: '系统管理',
      icon: 'system',
      roles: ['admin']
    },
    children: [
      {
        path: 'user',
        component: () => import('@/views/system/user/index'),
        name: 'User',
        meta: { title: '用户管理', icon: 'user' }
      },
      {
        path: 'role',
        component: () => import('@/views/system/role/index'),
        name: 'Role',
        meta: { title: '角色管理', icon: 'peoples' }
      },
      {
        path: 'menu',
        component: () => import('@/views/system/menu/index'),
        name: 'Menu',
        meta: { title: '菜单管理', icon: 'tree-table' }
      },
      {
        path: 'dept',
        component: () => import('@/views/system/dept/index'),
        name: 'Dept',
        meta: { title: '部门管理', icon: 'tree' }
      }
    ]
  },
  {
    path: '/monitor',
    component: Layout,
    name: 'Monitor',
    meta: {
      title: '系统监控',
      icon: 'monitor'
    },
    children: [
      {
        path: 'operlog',
        component: () => import('@/views/monitor/operlog/index'),
        name: 'Operlog',
        meta: { title: '操作日志', icon: 'log' }
      },
      {
        path: 'logininfor',
        component: () => import('@/views/monitor/logininfor/index'),
        name: 'Logininfor',
        meta: { title: '登录日志', icon: 'logininfor' }
      }
    ]
  },
  {
    path: '/tool',
    component: Layout,
    name: 'Tool',
    meta: {
      title: '系统工具',
      icon: 'tool'
    },
    children: [
      {
        path: 'build',
        component: () => import('@/views/tool/build/index'),
        name: 'FormBuild',
        meta: { title: '表单构建', icon: 'build' }
      },
      {
        path: 'gen',
        component: () => import('@/views/tool/gen/index'),
        name: 'Gen',
        meta: { title: '代码生成', icon: 'code' }
      }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes: constantRoutes,
  scrollBehavior(to, from, savedPosition) {
    if (savedPosition) {
      return savedPosition
    } else {
      return { top: 0 }
    }
  }
})

// 重置路由
export function resetRouter() {
  const newRouter = createRouter({
    history: createWebHistory(),
    routes: constantRoutes,
    scrollBehavior(to, from, savedPosition) {
      if (savedPosition) {
        return savedPosition
      } else {
        return { top: 0 }
      }
    }
  })
  router.matcher = newRouter.matcher
}

export default router