<template>
  <div class="user-info-page">
    <el-table :data="students" style="width: 100%">
      <el-table-column prop="name" label="姓名" width="180"></el-table-column>
      <el-table-column prop="studentId" label="学号" width="180"></el-table-column>
      <el-table-column prop="major" label="专业" width="180"></el-table-column>
      <el-table-column label="操作" width="220">
        <template #default="{ row }">
          <el-button
              :type="row.isBanned ? 'success' : 'danger'"
              @click="toggleBan(row)"
          >
            {{ row.isBanned ? '解封' : '封禁' }}
          </el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { ElTable, ElTableColumn, ElButton } from 'element-plus';

// 假设的学生信息数据列表
const students = ref([
  {
    name: '张三',
    studentId: '20240001',
    major: '计算机科学与技术',
    isBanned: false
  },
  {
    name: '李四',
    studentId: '20240002',
    major: '软件工程',
    isBanned: true
  },
  {
    name: '王五',
    studentId: '20240003',
    major: '人工智能',
    isBanned: false
  }
]);

// 切换封禁状态的方法
const toggleBan = (student) => {
  student.isBanned = !student.isBanned;
  // 这里可以添加调用后端API进行实际封禁或解封操作的代码
};
</script>

<style scoped>
.user-info-page {
  width: 100vh;
  height: 100vh; /* 使用视口高度单位铺满整个视口 */
  display: flex; /* 使用Flexbox布局 */
}

.el-table {
  width: 100%; /* 表格宽度铺满 */
  max-width: 100%; /* 表格最大宽度铺满 */
}
</style>
