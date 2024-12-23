<template>
  <el-container>
    <el-header>
      <h2>新增课程</h2>
    </el-header>
    <el-main>
      <el-form ref="courseForm" :model="courseForm" label-width="120px">
        <el-form-item label="课程名">
          <el-input v-model="courseForm.name"></el-input>
        </el-form-item>
        <el-form-item label="课程编号">
          <el-input v-model="courseForm.code"></el-input>
        </el-form-item>
        <el-form-item label="开课学院">
          <el-input v-model="courseForm.academy"></el-input>
        </el-form-item>
        <el-form-item label="任课老师">
          <el-input v-model="courseForm.teacher"></el-input>
        </el-form-item>
        <el-form-item label="授课时间">
          <el-checkbox-group v-model="courseForm.days" @change="handleDaysChange">
            <el-checkbox label="周一">周一</el-checkbox>
            <el-checkbox label="周二">周二</el-checkbox>
            <el-checkbox label="周三">周三</el-checkbox>
            <el-checkbox label="周四">周四</el-checkbox>
            <el-checkbox label="周五">周五</el-checkbox>
          </el-checkbox-group>
          <el-time-picker
              v-model="courseForm.startTime"
              :picker-options="timePickerOptions"
              placeholder="开始时间"
          ></el-time-picker>
          <el-time-picker
              v-model="courseForm.endTime"
              :picker-options="timePickerOptions"
              placeholder="结束时间"
          ></el-time-picker>
        </el-form-item>
        <el-form-item label="课程简介">
          <el-input
              type="textarea"
              v-model="courseForm.description"
              placeholder="请输入课程简介"
          ></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm">提交</el-button>
          <el-button @click="resetForm">重置</el-button>
        </el-form-item>
      </el-form>
    </el-main>
  </el-container>
</template>

<script setup>
import { ref } from 'vue';
import {
  ElForm,
  ElFormItem,
  ElInput,
  ElButton,
  ElCheckbox,
  ElCheckboxGroup,
  ElTimePicker,
  ElContainer,
  ElHeader,
  ElMain
} from 'element-plus';
const handleDaysChange = (value) => {
  console.log('选中的天数变化:', value);
};
const courseForm = ref({
  name: '',
  code: '',
  academy: '', // 新增的开课学院字段
  teacher: '',
  days: [],
  startTime: '',
  endTime: '',
  description: ''
});

const timePickerOptions = {
  selectableRange: '00:00:00 - 23:59:59'
};

const submitForm = () => {
  console.log('提交表单', courseForm.value);
  // 这里可以添加提交表单的逻辑，例如发送请求到后端
};

const resetForm = () => {
  courseForm.value = {
    name: '',
    code: '',
    academy: '', // 新增的开课学院字段
    teacher: '',
    days: [],
    startTime: '',
    endTime: '',
    description: ''
  };
};
</script>

<style>
</style>
