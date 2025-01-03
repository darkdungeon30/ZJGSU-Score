<script setup>
import { useRouter } from 'vue-router';
const router = useRouter();
const logout = () => {
  localStorage.removeItem('token');
  router.push('/');
};
</script>

<template>
  <header class="header">
    <div class="header-left">
      <h3 class="welcome-message">商大选课，破解版！</h3>
    </div>
    <div class="header-right">
      <div class="user-management" style="padding-right: 20px">
        <img class="avatar" src="https://tse2-mm.cn.bing.net/th/id/OIP-C.qYeM02sjAeO7kgvR6TDA8AAAAA?w=211&h=211&c=7&r=0&o=5&dpr=1.5&pid=1.7" alt="Avatar">
        <button @click="logout" class="logout-button" style="background-color: #0c4388">退出登录</button>
      </div>
    </div>
  </header>
</template>

<style scoped>
.header {
  display: flex;
  width: 100%;
  justify-content: space-between;
  align-items: center;
  height: 60px;
  background-color: var(--el-color-primary-light-9);
}

.user-management {
  display: flex;
  gap: 10px;
  align-items: center;
}

.avatar {
  width: 50px;
  height: 50px;
  border-radius: 50%;
  object-fit: cover;
}

.logout-button {
  padding: 5px 10px;
  background-color: #f44336;
  color: white;
  border: none;
  cursor: pointer;
}

.logout-button:hover {
  background-color: #d32f2f;
}
</style>
