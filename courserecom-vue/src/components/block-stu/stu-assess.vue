<template>
  <el-container class="course-list-container">
    <el-header class="course-list-header">
      <h2>课程列表</h2>
    </el-header>
    <el-main class="course-list-main">
      <el-container v-if="selectedCourse" class="course-details-container">
        <el-header class="course-details-header">
          <h2 style="text-align: center;">{{ selectedCourse.name }} - 课程均分详情</h2>
          <div class="chart-container">
            <div ref="radarChartOverall" class="chart"></div>
            <div ref="radarChartDetails" class="chart"></div>
          </div>
          <el-button @click="selectedCourse = null">关闭</el-button>

          <!-- 评分表单 -->
          <div class="rating-form">
            <el-form :model="ratingForm" label-width="120px">
              <el-form-item label="专业性">
                <el-input v-model="ratingForm.professionalism" type="number" min="0" max="10" placeholder="0-10" class="input-width"></el-input>
              </el-form-item>
              <el-form-item label="生动性">
                <el-input v-model="ratingForm.lively" type="number" min="0" max="10" placeholder="0-10" class="input-width"></el-input>
              </el-form-item>
              <el-form-item label="教师分">
                <el-input v-model="ratingForm.teacher" type="number" min="0" max="10" placeholder="0-10" class="input-width"></el-input>
              </el-form-item>
              <el-form-item label="作业适合分">
                <el-input v-model="ratingForm.homework" type="number" min="0" max="10" placeholder="0-10" class="input-width"></el-input>
              </el-form-item>
              <el-form-item label="难度分">
                <el-input v-model="ratingForm.difficulty" type="number" min="0" max="10" placeholder="0-10" class="input-width"></el-input>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click="submitRating">提交评分</el-button>
              </el-form-item>
            </el-form>
          </div>
        </el-header>
      </el-container>

      <div class="overlay" v-if="selectedCourse"></div>

      <el-row v-for="course in paginatedCourses" :key="course.name" class="course-item">
        <el-col :span="24">
          <div class="course-info">
            <h3>{{ course.name }}</h3>
            <p>开课学院：{{ course.department }}</p>
            <p>任课老师：{{ course.teacher }}</p>
            <p>授课时间：{{ formatTime(course.time) }}</p>
            <p>课程简介：{{ course.description }}</p>
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

// 假设的课程数据
const courses = ref([
  {
    name: '中国传统文化',
    teacher: '孙老师',
    time: ['周一', '14:00', '15:30'],
    description: '本课程旨在传承和弘扬中国传统文化，包括文学、艺术、哲学等多个方面。',
    scores: [7, 6, 5, 8, 9], // 个人评分
    scores_t: [8, 7, 6, 9, 8], // 综合评分
    department: '文学院'
  },
  {
    name: '西方哲学史',
    teacher: '周老师',
    time: ['周二', '16:00', '17:30'],
    description: '本课程主要介绍西方哲学的发展历史，探讨各个时期的哲学思想和流派。',
    scores: [5, 6, 7, 8, 9], // 个人评分
    scores_t: [7, 6, 5, 8, 7], // 综合评分
    department: '哲学院'
  },
  {
    name: '健康与营养学',
    teacher: '吴老师',
    time: ['周三', '10:00', '11:30'],
    description: '本课程旨在教授健康与营养的基本知识，帮助学生建立科学的健康观念和饮食习惯。',
    scores: [9, 7, 8, 6, 5], // 个人评分
    scores_t: [8, 8, 7, 7, 6], // 综合评分
    department: '食品学院'
  },
]);

const selectedCourse = ref(null);
const radarChartOverall = ref(null);
const radarChartDetails = ref(null);
const currentPage = ref(1);
const itemsPerPage = ref(4);
const totalCourses = ref(courses.value.length);
const ratingForm = ref({
  professionalism: null,
  lively: null,
  teacher: null,
  homework: null,
  difficulty: null
});

// 计算分页课程
const paginatedCourses = computed(() => {
  const start = (currentPage.value - 1) * itemsPerPage.value;
  const end = start + itemsPerPage.value;
  return courses.value.slice(start, end);
});

// 格式化授课时间的方法
function formatTime(time) {
  return `${time[0]} ${time[1]}-${time[2]}`;
}

// 显示课程详情和五维图
function showCourseDetails(course) {
  selectedCourse.value = course;
  updateRadarChart(course); // 显示课程详情时绘制雷达图
}

// 提交评分的方法
function submitRating() {
  if (selectedCourse.value) {
    const newScore = [
      ratingForm.value.professionalism,
      ratingForm.value.lively,
      ratingForm.value.teacher,
      ratingForm.value.homework,
      ratingForm.value.difficulty
    ];

    // 过滤掉无效评分（null或未定义的评分）
    const validScores = newScore.filter(score => score !== null && score !== undefined);

    // 只有在输入合法评分的情况下，将评分添加到课程中
    if (validScores.length > 0) {
      // 只更新已填写的评分项
      validScores.forEach((score, index) => {
        if (score !== null && score !== undefined) {
          selectedCourse.value.scores[index] = score;
        }
      });
      alert('评分提交成功！');

      // 更新综合评分

      // Reset the rating form
      ratingForm.value = {
        professionalism: null,
        lively: null,
        teacher: null,
        homework: null,
        difficulty: null
      };

      // 重新绘制雷达图
      updateRadarChart(selectedCourse.value); // 重新绘制雷达图
    } else {
      alert('请填写所有评分项。');
    }
  }
}
function updateRadarChart(course) {
  nextTick(() => {
    // 绘制综合评分雷达图
    const myChartOverall = echarts.init(radarChartOverall.value);
    const labelsOverall = ['专业性', '生动性', '教师分', '作业适合分', '难度分'];
    const scoresOverall = course.scores_t;

    const optionOverall = getRadarOption(labelsOverall, scoresOverall, '综合评分', '综合评分图');

    myChartOverall.setOption(optionOverall);

    // 绘制个人评分雷达图
    const myChartDetails = echarts.init(radarChartDetails.value);
    const scoresDetails = course.scores;

    const optionDetails = getRadarOption(labelsOverall, scoresDetails, '个人评分', '个人评分图');

    myChartDetails.setOption(optionDetails);
  });
}

// 处理页面变化
function handlePageChange(page) {
  currentPage.value = page;
}

// 绘制雷达图的通用选项
function getRadarOption(labels, scores, seriesName, title) {
  return {
    title: {
      text: title
    },
    tooltip: {},
    legend: {
      data: [seriesName]
    },
    radar: {
      indicator: labels.map(label => ({ name: label, max: 10 })),
      shape: 'circle'
    },
    series: [{
      name: seriesName,
      type: 'radar',
      data: [{
        value: scores,
        name: seriesName
      }]
    }]
  };
}
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

.rating-form {
  margin-top: 20px;
}

.rating-form .el-form-item {
  margin-bottom: 15px;
}

.chart-container {
  display: flex;
  justify-content: space-between;
  padding: 20px;
}

.chart {
  width: 45%;
  height: 400px;
}
.input-width {
  width: 100px; /* 或者您希望的任何宽度 */
}
</style>
