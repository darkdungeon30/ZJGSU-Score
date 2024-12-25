<template>
  <el-container>
    <el-header class="course-list-header">
      <h2>新增课程</h2>
    </el-header>
    <el-main>
      <div class="add-product-container">
        <el-form-item label="课程名">
          <el-input v-model="courseForm.lname" placeholder="请输入课程名" aria-required="true" @input="onInput"></el-input>
        </el-form-item>
        <el-form-item label="任课老师">
          <el-input v-model="courseForm.lteacher" placeholder="请输入任课老师" aria-required="true" @input="onInput"></el-input>
        </el-form-item>
        <el-form-item label="课程简介">
          <el-input
              type="textarea"
              v-model="courseForm.ldesc"
              placeholder="请输入课程简介"
              @input="onInput"
          ></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm">提交</el-button>
          <el-button @click="resetForm">重置</el-button>
        </el-form-item>
      </div>

    </el-main>
  </el-container>
</template>

<script setup>
import { ref } from 'vue';
import axios from 'axios';
import {
  ElMessage
} from 'element-plus';

const courseForm = ref({
  lname: '',
  lteacher: '',
  ldesc: ''
});

const submitForm = async () => {
  try {
    const response = await axios.post('http://localhost:8090/lesson/create', courseForm.value);
    if (response.data) {
      ElMessage.success("提交成功！");
    } else {
      ElMessage.error("提交失败，请重试！");
    }
  } catch (error) {
    console.error('Error:', error);
    ElMessage.error("提交失败，请检查网络或联系管理员！");
  }
};

const resetForm = () => {
  courseForm.value.lname = '';
  courseForm.value.lteacher = '';
  courseForm.value.ldesc = '';
  ElMessage.success("重置成功");
};

function onInput() {
  this.$forceUpdate();
}
</script>

<style>
.course-list-header h2 {
  margin: 0;
  color: var(--el-color-primary);
}
</style>
