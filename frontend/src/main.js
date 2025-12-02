import { createApp } from 'vue'
import App from './App.vue'
import router from './router' // @/api/router
import { setupI18n } from './lang' // internationalization
import { usePermissionStore } from './store/modules/permission'
import { useDictStore } from './store/modules/dict'
import dict from './utils/dict'
import './permission' // permission control
import './assets/icons' // icon
import './assets/styles/index.scss' // global css
import './permission' // permission control

/**
 * 输出权限指令
 */
// v-auth指令在全局权限控制文件中定义

/**
 * 输出角色指令
 */
// v-role指令在全局权限控制文件中定义

// 全局方法挂载
app.config.globalProperties.$http = request

app.mount('#app')
setupPermissionStore()
setupI18n()
useDictStore().getDict().then(response => {
  dict(response)
})

// 4.0版本兼容处理
app.use(router)