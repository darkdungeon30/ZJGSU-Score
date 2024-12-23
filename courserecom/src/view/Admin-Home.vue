<template>
  <!-- 根容器 -->
  <el-container class="layout-container-demo" style="height: 100%;display: flex;flex-direction: column;">
    <div style="height: 100%;display: flex;">
      <el-header style="width: 100%;text-align: right; font-size: 12px; display: flex; align-items: center; height: 80px; background-color: var(--el-color-primary-light-9);">
        <adminheader class="el-menu"></adminheader>
      </el-header>
    </div>
    <!-- 侧边栏 -->
    <div style="height: 100%;display: flex;">
      <el-aside width="300px" height="100%">
        <adMenu @menu-click="handleMenuClick" class="el-aside"></adMenu>
      </el-aside>
      <!-- 主区域 -->
      <el-container>
        <!-- 主要内容区域 -->
        <el-main style="height: 100%">
          <div style="height: 400px;display: contents">
            <component :is="currentComponent"></component>
          </div>
        </el-main>
      </el-container>
    </div>
  </el-container>
</template>

<script lang="ts" setup>
import { ref, onMounted } from 'vue';
import adMenu from "@/components/block-admin/admin-menu.vue";
import adminheader from "@/components/block-admin/admin-header.vue";
import UserInfo from "@/components/block-admin/admin-users.vue";
import AllCourse from "@/components/block-admin/admin-course.vue";
import newCourse from "@/components/block-admin/admin-newCourse.vue";

// 定义一个响应式变量来存储当前显示的组件
const currentComponent = ref(UserInfo);

const handleMenuClick = (index) => {
  switch (index) {
    case '1':
      currentComponent.value = UserInfo;
      break;
    case '2':
      currentComponent.value = AllCourse;
      break;
    case '3':
      currentComponent.value = newCourse;
      break;
  }
};
</script>

<style scoped>
/* 样式定义 */
.layout-container-demo {
  height: 100vh; /* 使用视口高度单位确保容器高度铺满整个视口 */
  display: flex;
  flex-direction: column;
}
.el-aside {
  width: 300px; /* 侧边栏宽度固定为300px */
  height: 100vh; /* 使用视口高度单位确保侧边栏高度铺满整个视口 */
}
.el-menu {
  width: 100%;
  padding: 0;
  border-right: none;
}
.layout-container-demo .el-main {
  padding: 0;
}
.layout-container-demo .toolbar {
  display: flex;
  align-items: center;
  justify-content: flex-end;
  height: 100%;
}
html, body, #app {
  height: 100%;
  margin: 0;
}

.logo img {
  max-width: 100%; /* Ensure the logo scales with the container */
  height: 100%; /* Ensures logo height matches container */
}
</style>
