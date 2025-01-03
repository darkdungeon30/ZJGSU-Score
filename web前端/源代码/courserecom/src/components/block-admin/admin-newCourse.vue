<template>
  <el-container>
    <el-header class="course-list-header">
      <h2>新增课程</h2>
    </el-header>
    <el-main>
      <el-form ref="courseForm" :model="courseForm" label-width="120px">
        <el-form-item label="课程名">
          <el-input v-model="courseForm.name" placeholder="请输入课程名" aria-required="true" @input="onInput"></el-input>
        </el-form-item>
        <el-form-item label="课程编号">
          <el-input v-model="courseForm.code" placeholder="请输入课程编号" aria-required="true" @input="onInput"></el-input>
        </el-form-item>
        <el-form-item label="开课学院">
          <el-input v-model="courseForm.academy" placeholder="请输入开课学院" aria-required="true" @input="onInput"></el-input>
        </el-form-item>
        <el-form-item label="任课老师">
          <el-input v-model="courseForm.teacher" placeholder="请输入任课老师" aria-required="true" @input="onInput"></el-input>
        </el-form-item>
        <el-form-item label="授课时间">
          <el-checkbox-group v-model="courseForm.days" @change="handleDaysChange">
            <el-checkbox label="周一" @change="handleCheckedChange()">周一</el-checkbox>
            <el-checkbox label="周二" @change="handleCheckedChange()">周二</el-checkbox>
            <el-checkbox label="周三" @change="handleCheckedChange()">周三</el-checkbox>
            <el-checkbox label="周四" @change="handleCheckedChange()">周四</el-checkbox>
            <el-checkbox label="周五" @change="handleCheckedChange()">周五</el-checkbox>
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
              @input="onInput"
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

// 定义一个本地的courses数组来存储课程数据
const courses = ref([]);

const timePickerOptions = {
  selectableRange: '00:00:00 - 23:59:59'
};

const submitForm = () => {
  console.log('提交表单', courseForm.value);
  // 将courseForm中的数据添加到courses数组中
  courses.value.push(courseForm.value);
  alert("提交成功！")
  resetForm();
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
  alert("重置成功")
};

function onInput() {
  this.$forceUpdate();
  }

function handleCheckedChange() {
  this.$forceUpdate();
}
</script>

<style>
.course-list-header h2 {
  margin: 0;
  color: var(--el-color-primary);
}
</style>
