<template>
  <div class="user-info-page">
    <el-header class="course-list-header">
      <h2>用户列表</h2>
    </el-header>
    <div style="display: flex">
      <el-input
          v-model="search"
          type="text"
          placeholder="请输入姓名/学号进行搜索"
          class="search-input">
      </el-input>
      <el-button slot="append" icon="el-icon-search" @click="searchUser">搜索</el-button>
    </div>
    <el-table :data="filteredStudents" style="width: 100%">
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
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue';
import { ElTable, ElTableColumn, ElButton, ElInput, ElMessage } from 'element-plus';
import axios from 'axios';

const search = ref('');
const students = ref([]);
const searchQuery = ref('');

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
      fetchStudents(); // 重新获取用户列表
    } else {
      ElMessage.error("更新失败！！");
    }
  } catch (error) {
    console.error('Error updating student:', error);
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
</style>
