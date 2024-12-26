<template>
  <el-container class="user-info-container">
    <el-header class="user-info-header">
      <h2>用户信息</h2>
    </el-header>
    <el-main class="user-info-main">
      <el-row class="user-info">
        <el-col :span="12">
          <div class="info-item">
            <span class="info-label">学号:</span>
            <span class="info-value">{{ claims.account }}</span>
          </div>
        </el-col>
        <el-col :span="12">
          <div class="info-item">
            <span class="info-label">姓名:</span>
            <span class="info-value">{{ claims.name }}</span>
          </div>
        </el-col>
      </el-row>
      <div class="preference-chart">
        <div ref="radarChart" style="width: 100%; height: 400px;"></div>
      </div>
      <!-- 新增按钮 -->
      <el-button v-if="showForm" @click="showForm = !showForm">取消</el-button>
      <el-button v-if="!showForm" @click="showForm = !showForm">修改偏好</el-button>
      <!-- 新增表单 -->
      <el-form v-if="showForm" label-width="100px">
        <el-form-item label="专业性">
          <el-input-number v-model="preferences[0].score" :min="0" :max="10"></el-input-number>
        </el-form-item>
        <el-form-item label="趣味性">
          <el-input-number v-model="preferences[1].score" :min="0" :max="10"></el-input-number>
        </el-form-item>
        <el-form-item label="给分情况">
          <el-input-number v-model="preferences[2].score" :min="0" :max="10"></el-input-number>
        </el-form-item>
        <el-form-item label="期末难度">
          <el-input-number v-model="preferences[3].score" :min="0" :max="10"></el-input-number>
        </el-form-item>
        <el-form-item label="难度分">
          <el-input-number v-model="preferences[4].score" :min="0" :max="10"></el-input-number>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitPreferences">更新</el-button>
        </el-form-item>
      </el-form>
    </el-main>
  </el-container>
</template>

<script setup>
import {ref, onMounted} from 'vue';
import * as echarts from 'echarts';
import {jwtDecode} from "jwt-decode";
import axios from 'axios';

const token = localStorage.getItem('token');
const claims = jwtDecode(token);
const preferences = ref([
  {label: '专业性', score: 0},
  {label: '趣味性', score: 0},
  {label: '给分情况', score: 0},
  {label: '期末难度', score: 0},
  {label: '难度分', score: 0}
]);

const radarChart = ref(null);
const showForm = ref(false);

onMounted(async () => {
  const uid = claims.id; // 确保你的JWT中有uid字段
  const response = await axios.get(`http://localhost:8090/user/info/${uid}`);
  const userData = response.data; // 假设后端返回的数据结构
  console.log("用户数据：",userData);
  // 映射数据到preferences
  preferences.value = [
    {label: '专业性', score: userData.uspecial},
    {label: '趣味分', score: userData.ufun},
    {label: '给分情况', score: userData.uscore},
    {label: '期末难度', score: userData.uexam},
    {label: '难度分', score: userData.udifficult}
  ];

  const myChart = echarts.init(radarChart.value);
  const labels = preferences.value.map(item => item.label);
  const scores = preferences.value.map(item => item.score);

  const option = {
    title: {
      text: '选课偏好五维图'
    },
    tooltip: {},
    legend: {
      data: ['偏好评分']
    },
    radar: {
      indicator: labels.map(label => ({name: label, max: 10})),
      shape: 'circle'
    },
    series: [{
      name: '偏好评分',
      type: 'radar',
      data: [{
        value: scores,
        name: '偏好评分'
      }]
    }]
  };

  myChart.setOption(option);
});
async function submitPreferences() {
  try {
    const uid = claims.id; // 确保你的JWT中有uid字段
    const systemUser = {
      uid: uid,
      uspecial: preferences.value[0].score,
      ufun: preferences.value[1].score,
      uscore: preferences.value[2].score,
      uexam: preferences.value[3].score,
      udifficult: preferences.value[4].score
    };

    const response = await axios.post('http://localhost:8090/user/update', systemUser);
    if (response.data) {
      console.log('偏好更新成功');
      updateChartData(); // 更新图表数据
      showForm.value = false; // 隐藏表单
    } else {
      // 处理错误情况
      console.error('偏好更新失败');
    }
  } catch (error) {
    // 处理异常情况
    console.error('偏好更新异常', error);
  }
}
// 新增更新图表的方法
function updateChartData() {
  const myChart = echarts.getInstanceByDom(radarChart.value);
  const scores = preferences.value.map(item => item.score);
  myChart.setOption({
    series: [{
      data: [{
        value: scores,
        name: '偏好评分'
      }]
    }]
  });
}
</script>

<style scoped>
.user-info-container {
  padding: 20px;
}

.user-info-main {
  padding: 20px;
}

.info-item {
  display: flex;
  align-items: center;
  margin-bottom: 10px;
}

.info-label {
  font-weight: bold;
  margin-right: 10px;
}

.info-value {
  flex-grow: 1;
}

.preference-chart {
  margin-top: 20px;
}

.el-progress {
  margin-bottom: 20px;
}
</style>
