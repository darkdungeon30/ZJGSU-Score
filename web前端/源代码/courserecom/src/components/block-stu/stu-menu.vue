<template>
  <el-menu
      class="el-menu"
      :default-openeds="['1', '6']"
      style="background-color: var(--el-color-primary-light-9)"
      :collapse="isCollapse"
      @select="handleSelect"
  >
    <el-menu-item-group>
      <el-menu-item index="1">个人信息</el-menu-item>
    </el-menu-item-group>
    <el-menu-item-group>
      <el-menu-item index="2">所有课程</el-menu-item>
    </el-menu-item-group>
    <el-menu-item-group>
      <el-menu-item index="3">智能推荐</el-menu-item>
    </el-menu-item-group>
    <el-menu-item-group>
      <el-menu-item index="4">我的课程</el-menu-item>
    </el-menu-item-group>
    <el-menu-item-group>
      <el-menu-item index="5">评分历史</el-menu-item>
    </el-menu-item-group>
  </el-menu>
</template>

<script setup>
import { defineEmits } from 'vue';

const emits = defineEmits(['menu-click', 'shelf-click', 'order-click']);

const handleSelect = (index) => {
  emits('menu-click', index);
};
</script>

<style scoped>
html, body {
  height: 100%;
  margin: 0;
}

.el-menu {
  height: 100%;
}
</style>
