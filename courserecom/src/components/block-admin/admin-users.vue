<template>
  <div class="user-info-page">
    <el-header class="course-list-header">
      <h2>用户列表</h2>
    </el-header>
    <div style="display: flex">
      <el-input
          placeholder="请输入姓名/学号进行搜索"
          class="search-input">
      </el-input>
      <el-button slot="append" icon="el-icon-search">搜索</el-button>
    </div>
    <el-table :data="filteredStudents" style="width: 100%">
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
import { ref, computed } from 'vue';
import { ElTable, ElTableColumn, ElButton, ElInput } from 'element-plus';

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

const searchQuery = ref(''); // 定义搜索查询变量

// 计算属性根据搜索查询过滤学生列表
const filteredStudents = computed(() => {
  if (!searchQuery.value) {
    return students.value;
  }
  return students.value.filter(student => {
    return student.name.includes(searchQuery.value) ||
        student.studentId.includes(searchQuery.value);
  });
});

// 切换封禁状态的方法
const toggleBan = (student) => {
  student.isBanned = !student.isBanned;
  // 这里可以添加调用后端API进行实际封禁或解封操作的代码
};

// 搜索处理方法
const handleSearch = () => {
  // 目前该方法用于更新计算属性，无需具体实现
};
</script>

<style scoped>
.user-info-page {
  width: 100vh;
  height: 100vh; /* 使用视口高度单位铺满整个视口 */
  display: flex; /* 使用Flexbox布局 */
  flex-direction: column; /* 使内容纵向排列 */
}

.title {
  text-align: center; /* 标题居中 */
  margin-bottom: 20px; /* 标题与搜索框间留出间距 */
}

.search-container {
  display: flex; /* 使用Flexbox布局 */
  justify-content: center; /* 中心对齐 */
  margin-bottom: 20px; /* 搜索框与表格间留出间距 */
}

.search-input {
  width: 250px; /* 设置搜索框宽度 */
  margin-right: 10px; /* 搜索框与按钮间留出减少的间距 */
}

.el-table {
  width: 100%; /* 表格宽度铺满 */
  max-width: 100%; /* 表格最大宽度铺满 */
}
.course-list-header h2 {
  margin: 0;
  color: var(--el-color-primary);
}

</style>
