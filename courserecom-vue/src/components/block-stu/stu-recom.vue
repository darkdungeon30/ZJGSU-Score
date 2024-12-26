<template>
  <el-container class="course-list-container">
    <el-header class="course-list-header">
      <h2>课程列表</h2>
    </el-header>
    <el-main class="course-list-main">
      <el-container v-if="selectedCourse" class="course-details-container">
        <el-header class="course-details-header">
          <h2 style="text-align: center;">{{ selectedCourse.name }} - 课程均分详情</h2>
          <div class="radar-charts">
            <div ref="radarChart" style="width: 50%; height: 400px; display: inline-block;"></div>
            <div ref="radarChartStu" style="width: 50%; height: 400px; display: inline-block;"></div>
          </div>
          <el-button @click="selectedCourse = null">关闭</el-button>
        </el-header>
      </el-container>

      <div class="overlay" v-if="selectedCourse"></div>

      <el-row v-for="course in paginatedCourses" :key="course.lid" class="course-item">
        <el-col :span="24">
          <div class="course-info">
            <h3>{{ course.lname }}</h3>
            <p>任课老师：{{ course.lteacher }}</p>
            <p>课程简介：{{ course.ldesc }}</p>
            <p>匹配分数：{{ course.score.toFixed(2) }} %</p>
          </div>
        </el-col>
        <el-col :span="6" class="course-actions">
          <div style="display: flex; flex-direction: column;text-align: center">
            <el-button type="primary" @click="showCourseDetails(course)" style="width: 100px;margin: 10px;">查看详细</el-button>
          </div>
        </el-col>
      </el-row>
    </el-main>
    <el-pagination
        v-if="totalCourses > itemsPerPage"
        :current-page="currentPage"
        :page-size="itemsPerPage"
        :total="totalCourses"
        @current-change="handlePageChange"
        layout="total, prev, pager, next"
        style="margin-top: 20px; text-align: center;"
    />
  </el-container>
</template>

<script setup>
import { ref, onMounted, nextTick, computed } from 'vue';
import * as echarts from 'echarts';
import { jwtDecode } from "jwt-decode";
import axios from 'axios';
import { ElMessage } from "element-plus";

const token = localStorage.getItem('token');
const claims = jwtDecode(token);
const courses = ref([]);
const selectedCourse = ref(null);
const radarChart = ref(null);
const radarChartStu = ref(null);
const currentPage = ref(1);
const itemsPerPage = ref(3);
const totalCourses = ref(0);
const userPreferences = ref([]);

const paginatedCourses = computed(() => {
  const start = (currentPage.value - 1) * itemsPerPage.value;
  const end = start + itemsPerPage.value;
  return courses.value.slice(start, end);
});

async function fetchEvaluates(course) {
  try {
    const response = await axios.get(`http://localhost:8090/evaluate/get_by_lid/${course.lid}`);
    if (!response.data) {
      throw new Error("No data received");
    }
    return response.data;
  } catch (error) {
    console.error('Fetching evaluates failed:', error);
    ElMessage.error("获取评分详情失败，请检查网络或联系管理员！");
    return [];
  }
}

function calcCourseScores(evaluates) {
  const scores = evaluates;
  const scoreKeys = ['especial', 'efun', 'escore', 'eexam', 'edifficult', 'erecommend'];
  const labels = ['专业性', '趣味分', '给分情况', '期末难度', '难度分', '推荐指数'];
  return scoreKeys.map((key, index) => {
    const scoresForItem = scores.map(evaluate => parseFloat(evaluate[key].toFixed(2)));
    return {
      name: labels[index], // 使用中文标签
      value: scoresForItem.reduce((sum, score) => sum + score, 0) / scores.length
    };
  });
}

async function fetchUserPrefs(uid) {
  try {
    const response = await axios.get(`http://localhost:8090/user/info/${uid}`);
    return response.data;
  } catch (error) {
    console.error('获取用户偏好数据失败:', error);
    ElMessage.error("获取用户偏好失败，请检查网络或联系管理员！");
    return null;
  }
}

async function renderPrefsChart(preferences) {
  await nextTick(() => {
    const myChartStu = echarts.init(radarChartStu.value);
    const labels = ['专业性', '趣味分', '给分情况', '期末难度', '难度分'];
    const scores = [
      preferences.uspecial,
      preferences.ufun,
      preferences.uscore,
      preferences.uexam,
      preferences.udifficult
    ];

    const optionStu = {
      tooltip: {},
      legend: {
        data: ['偏好评分']
      },
      radar: {
        indicator: labels.map((label, index) => ({
          name: label,
          max: 10
        }))
      },
      series: [{
        name: '偏好评分',
        type: 'radar',
        data: [{
          value: scores,
          name: '用户偏好'
        }]
      }]
    };

    myChartStu.setOption(optionStu);
  });
}

async function showCourseDetails(course) {
  selectedCourse.value = course;
  const evaluates = await fetchEvaluates(course);
  const scoreData = calcCourseScores(evaluates).map(item => ({
    ...item,
    value: parseFloat(item.value.toFixed(2))
  }));

  const preferences = await fetchUserPrefs(claims.id);
  if (preferences) {
    userPreferences.value = preferences;
    await renderPrefsChart(preferences);
  }

  await nextTick(() => {
    const myChart = echarts.init(radarChart.value);
    const option = {
      tooltip: {},
      legend: {
        data: ['课程评分']
      },
      radar: {
        indicator: scoreData.filter(item => item.name !== '推荐指数').map(item => ({
          name: item.name,
          max: 10
        }))
      },
      series: [{
        name: '课程评分',
        type: 'radar',
        data: [{
          value: scoreData.filter(item => item.name !== '推荐指数').map(item => item.value),
          name: '课程评分'
        }]
      }],
      title: [{
        text: '课程综合评价',
        subtext: scoreData.find(item => item.name === '推荐指数').value.toFixed(2),
        left: 'right',
        top: '50%',
        textStyle: {
          color: '#333',
          fontSize: 16
        },
        subtextStyle: {
          color: '#666',
          fontSize: 14
        }
      }]
    };

    myChart.setOption(option);
  });
}

function handlePageChange(page) {
  currentPage.value = page;
}

async function fetchCourses() {
  try {
    const response = await axios.get(`http://localhost:8090/recommend/${claims.id}`);
    const recommendations = response.data;
    const filteredLids = Object.entries(recommendations)
        .filter(([lid, score]) => score >= 0.8)
        .map(([lid]) => lid);
    const coursePromises = filteredLids.map(lid => axios.get(`http://localhost:8090/lesson/info/${lid}`));
    const courseResponses = await Promise.all(coursePromises);
    courses.value = courseResponses.map((res, index) => {
      const courseData = res.data;
      const lid = filteredLids[index];
      const score = recommendations[lid]*100;
      courseData.score = score;
      return courseData;
    });
    courses.value.sort((a, b) => b.score - a.score);
    totalCourses.value = courses.value.length;
  } catch (error) {
    console.error('获取课程数据失败:', error);
  }
}

onMounted(async () => {
  await fetchCourses();
  const preferences = await fetchUserPrefs(claims.id);
  if (preferences) {
    userPreferences.value = preferences;
    await renderPrefsChart(preferences);
  }
});
</script>

<style scoped>
.course-list-container {
  padding: 20px;
}

.course-list-header h2 {
  margin: 0;
  color: var(--el-color-primary);
}

.course-list-main {
  padding: 20px;
}

.course-item {
  border-bottom: 1px solid #ebeef5;
  padding: 10px 0;
  margin-bottom: 10px;
  cursor: pointer;
}

.course-info h3 {
  margin: 0 0 10px 0;
  color: #333;
}

.course-info p {
  margin: 0 0 10px 0;
  color: #666;
}

.course-details-container {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgba(255, 255, 255, 0.9); /* 不透明白色背景 */
  z-index: 10;
  padding: 20px;
}

.course-details-header h2 {
  margin: 0;
  color: var(--el-color-primary);
}

.overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgba(0, 0, 0, 0.5); /* 半透明黑色背景 */
  z-index: 5; /* 使覆盖层在课程列表上方 */
}

.preference-chart {
  margin-top: 20px;
}
</style>
