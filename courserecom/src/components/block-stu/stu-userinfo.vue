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
            <span class="info-value">{{ userInfo.studentId }}</span>
          </div>
        </el-col>
        <el-col :span="12">
          <div class="info-item">
            <span class="info-label">专业:</span>
            <span class="info-value">{{ userInfo.major }}</span>
          </div>
        </el-col>
      </el-row>
      <div class="preference-chart">
        <div ref="radarChart" style="width: 100%; height: 400px;"></div>
      </div>
      <!-- 新增按钮 -->
      <el-button @click="showForm = !showForm">修改偏好</el-button>
      <!-- 新增表单 -->
      <el-form v-if="showForm" label-width="100px">
        <el-form-item label="专业性">
          <el-input-number v-model="preferences[0].score" :min="0" :max="10"></el-input-number>
        </el-form-item>
        <el-form-item label="生动性">
          <el-input-number v-model="preferences[1].score" :min="0" :max="10"></el-input-number>
        </el-form-item>
        <el-form-item label="教师分">
          <el-input-number v-model="preferences[2].score" :min="0" :max="10"></el-input-number>
        </el-form-item>
        <el-form-item label="作业适合分">
          <el-input-number v-model="preferences[3].score" :min="0" :max="10"></el-input-number>
        </el-form-item>
        <el-form-item label="难度分">
          <el-input-number v-model="preferences[4].score" :min="0" :max="10"></el-input-number>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="updateChartData">更新图表</el-button>
        </el-form-item>
      </el-form>
    </el-main>
  </el-container>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import * as echarts from 'echarts';

const userInfo = ref({
  studentId: '20240001',
  major: '计算机科学与技术'
});

const preferences = ref([
  { label: '专业性', score: 8 },
  { label: '生动性', score: 7 },
  { label: '教师分', score: 9 },
  { label: '作业适合分', score: 6 },
  { label: '难度分', score: 5 }
]);

const radarChart = ref(null);
const showForm = ref(false);

onMounted(() => {
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
      indicator: labels.map(label => ({ name: label, max: 10 })),
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

.user-info-header h2 {
  margin: 0;
  color: var(--el-color-primary);
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
