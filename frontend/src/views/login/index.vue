<template>
  <div class="login">
    <el-form ref="loginRef" :model="loginForm" :rules="loginRules" class="login-form">
      <h3 class="title">API管理后台平台</h3>
      <el-form-item prop="username">
        <el-input
          v-model="loginForm.username"
          type="text"
          size="large"
          auto-complete="off"
          placeholder="账号"
        >
          <template #prefix><svg-icon icon-class="user" class="el-input__icon" /></template>
        </el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input
          v-model="loginForm.password"
          type="password"
          size="large"
          auto-complete="off"
          placeholder="密码"
          @keyup.enter="handleLogin"
        >
          <template #prefix><svg-icon icon-class="password" class="el-input__icon" /></template>
        </el-input>
      </el-form-item>
      <el-form-item prop="code" v-if="captchaEnabled">
        <el-input
          v-model="loginForm.code"
          size="large"
          auto-complete="off"
          placeholder="验证码"
          style="width: 63%"
          @keyup.enter="handleLogin"
        >
          <template #prefix><svg-icon icon-class="validCode" class="el-input__icon" /></template>
        </el-input>
        <div class="login-code">
          <img :src="codeUrl" class="login-code-img" @click="getCode" />
        </div>
      </el-form-item>
      <el-checkbox v-model="loginForm.rememberMe" style="margin:0px 0px 25px 0px 0px;">记住密码</el-checkbox>
      <el-form-item style="width:100%;">
        <el-button
          :loading="loading"
          size="large"
          type="primary"
          style="width:100%;"
          @click="handleLogin"
        >
          <span v-if="!loading">登 录</span>
          <span v-else>登 录 中...</span>
        </el-button>
      </el-form-item>
    </el-form>
    <!--  底部 -->
    <div class="el-login-footer">
      <span>Copyright © 2023 API管理后台平台 All Rights Reserved.</span>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useUserStore } from '@/store/modules/user'
import { getCodeImg } from '@/api/login'
import Cookies from 'js-cookie'

const userStore = useUserStore()

const loginRef = ref(null)
const loginForm = reactive({
  username: 'admin',
  password: 'admin123',
  rememberMe: false,
  code: '',
  uuid: ''
})

const loginRules = {
  username: [
    { required: true, trigger: 'blur', message: '请输入您的账号' }
  ],
  password: [
    { required: true, trigger: 'blur', message: '请输入您的密码' }
  ],
  code: [{ required: true, trigger: 'change', message: '请输入验证码' }]
}

const loading = ref(false)
// 验证码开关
const captchaEnabled = ref(true)
// 验证码图片
const codeUrl = ref('')

const handleLogin = () => {
  loginRef.value?.validate(valid => {
    if (valid) {
      loading.value = true
      // 调用action的登录方法
      userStore.login(loginForm).then(() => {
        router.push({ path: '/' })
      }).catch(() => {
        loading.value = false
      })
    }
  })
}

const getCode = () => {
  getCodeImg().then(res => {
    codeUrl.value = res.img
    loginForm.uuid = res.uuid
  })
}
</script>

<style lang="scss" scoped>
.login {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  background-image: url("../assets/images/login-background.jpg");
  background-size: cover;
  position: relative;
}

.title {
  margin: 0px auto 30px auto;
  text-align: center;
  color: #333;
}

.login-form {
  border-radius: 6px;
  background: #ffffff;
  width: 400px;
  padding: 25px 25px 5px 25px;

  .el-input {
    height: 40px;

    input {
      height: 40px;
    }
  }

  .input-icon {
    height: 39px;
    width: 14px;
    margin-left: 2px;
  }

  .el-form-item {
    margin-bottom: 20px;
  }
}

.login-tip {
  font-size: 13px;
  text-align: center;
  color: #bfbfbf;
}

.login-code {
  width: 33%;
  height: 40px;
  float: right;

  img {
    cursor: pointer;
    vertical-align: middle;
  }
}

.el-login-footer {
  height: 40px;
  line-height: 40px;
  position: fixed;
  bottom: 0;
  width: 100%;
  text-align: center;
  color: #fff;
  font-family: Arial;
  font-size: 12px;
  letter-spacing: 1px;
}
</style>