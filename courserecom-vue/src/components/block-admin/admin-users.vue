<template>
  <div class="user-info-page">
    <el-header class="course-list-header">
      <h2>用户列表</h2>
    </el-header>
    <div style="display: flex;">
      <el-input
          v-model="search"
          type="text"
          placeholder="请输入姓名/学号进行搜索"
          class="search-input">
      </el-input>
      <el-button slot="append" icon="el-icon-search" @click="searchUser">搜索</el-button>
    </div>
    <div style="display: flex; flex: 1;width: 1400px">
      <el-table :data="filteredStudents" style="width: 70%;">
        <el-table-column prop="uname" label="学生姓名" width="180"></el-table-column>
        <el-table-column prop="uaccount" label="学号" width="180"></el-table-column>
        <el-table-column label="操作" width="220">
          <template #default="{ row }">
            <el-button
                :type="row.utype === 1 ? 'danger' : 'success'"
                @click="toggleBan(row)"
            >
              {{ row.utype === 1 ? '封禁' : '解封' }}
            </el-button>
            <el-button
                @click="viewScoreHistory(row.uid)"
                style="margin-left: 10px;"
            >
              查看评分历史
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <div v-if="isVisible" class="score-history" style="margin-left: 20px;">
        <h3>评分历史</h3>
        <el-table :data="scores" style="width: 100%">
          <el-table-column prop="lid" label="课程ID" width="100"></el-table-column>
          <el-table-column prop="especial" label="专业性" width="100"></el-table-column>
          <el-table-column prop="efun" label="生动性" width="100"></el-table-column>
          <el-table-column prop="edifficult" label="难度分" width="100"></el-table-column>
          <el-table-column prop="escore" label="给分情况" width="100"></el-table-column>
          <el-table-column prop="eexam" label="期末难度" width="100"></el-table-column>
          <el-table-column prop="erecommend" label="推荐" width="100"></el-table-column>
        </el-table>
      </div>
    </div>
  </div>
</template>
<script setup>
import { ref, computed, onMounted } from 'vue';
import { ElTable, ElTableColumn, ElButton, ElInput, ElMessage } from 'element-plus';
import axios from 'axios';

const search = ref('');
const students = ref([]);
const searchQuery = ref('');
const scores = ref([]); // 用于存储评分历史
const isVisible = ref(false);
const fetchStudents = async () => {
  try {
    const response = await axios.get('http://localhost:8090/user/list');
    // 过滤掉 utype 为 0 的用户
    students.value = response.data.filter(student => student.utype !== 0);
  } catch (error) {
    console.error('Error fetching students:', error);
  }
};

onMounted(fetchStudents);

const filteredStudents = computed(() => {
  if (!searchQuery.value) {
    return students.value;
  }
  return students.value.filter(student => {
    return student.uname.includes(searchQuery.value) || student.uaccount.toString().includes(searchQuery.value);
  });
});

const toggleBan = async (student) => {
  try {
    const data = {
      uid: student.uid,
      utype: student.utype === 1 ? 2 : 1
    };
    console.log("更新后状态", data);
    const response = await axios.post('http://localhost:8090/user/update', data);
    if (response.data) {
      if (student.utype === 1) {
        ElMessage.success("封禁完成");
      } else {
        ElMessage.success("解封完成");
      }
      await fetchStudents(); // 重新获取用户列表
    } else {
      ElMessage.error("更新失败！！");
    }
  } catch (error) {
    console.error('Error updating student:', error);
  }
};

const viewScoreHistory = async (uid) => {
  try {
    const response = await axios.get(`http://localhost:8090/evaluate/get_by_uid/${uid}`);
    scores.value = response.data; // 保存评分历史
    isVisible.value = !isVisible.value;
  } catch (error) {
    console.error('Error fetching score history:', error);
    ElMessage.error('获取评分历史失败');
  }
};

function searchUser() {
  searchQuery.value = search.value;
  fetchStudents(); // 重新获取用户列表并进行搜索
}
</script>
<style scoped>
.user-info-page {
  width: 100vh;
  height: 100vh;
  display: flex;
  flex-direction: column;
}

.title {
  text-align: center;
  margin-bottom: 20px;
}

.search-container {
  display: flex;
  justify-content: center;
  margin-bottom: 20px;
}

.search-input {
  width: 250px;
  margin-right: 10px;
}

.el-table {
  width: 100%;
  max-width: 100%;
}

.course-list-header h2 {
  margin: 0;
  color: var(--el-color-primary);
}

.score-history {
  display: flex;
  flex-direction: column;
  width: 100%; /* 让评分历史部分宽度自适应 */
  max-height: 400px; /* 可选的最大高度，控制表格的展示 */
  overflow-y: auto; /* 当内容超出的时候，允许滚动 */
}
</style>
