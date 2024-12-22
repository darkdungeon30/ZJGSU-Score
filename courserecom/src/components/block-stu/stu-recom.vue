<template>
  <el-container class="course-list-container">
    <el-header class="course-list-header">
      <h2>课程列表</h2>
    </el-header>
    <el-main class="course-list-main">
      <el-container v-if="selectedCourse" class="course-details-container">
        <el-header class="course-details-header">
          <h2 style="text-align: center;">{{ selectedCourse.name }} - 课程均分详情</h2>
          <div ref="radarChart" style="width: 100%; height: 400px;"></div>
          <el-button @click="selectedCourse = null">关闭</el-button>
        </el-header>
      </el-container>

      <div class="overlay" v-if="selectedCourse"></div>

      <el-row v-for="course in paginatedCourses" :key="course.name" class="course-item" @click="showCourseDetails(course)">
        <el-col :span="24">
          <div class="course-info">
            <h3>{{ course.name }}</h3>
            <p>任课老师：{{ course.teacher }}</p>
            <p>授课时间：{{ formatTime(course.time) }}</p>
            <p>课程简介：{{ course.description }}</p>
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
    name: '现代经济学原理',
    teacher: '王老师',
    time: ['周三', '14:00', '15:30'],
    description: '本课程主要讲解现代经济学的基本原理和方法，分析市场经济的运行机制和政策影响。',
    scores: [7, 8, 9, 5, 6]
  },
  {
    name: '音乐鉴赏',
    teacher: '郑老师',
    time: ['周四', '10:00', '11:30'],
    description: '本课程旨在培养学生的音乐鉴赏能力，介绍不同音乐风格和作品。',
    scores: [8, 9, 7, 6, 5]
  },
  {
    name: '现代管理学',
    teacher: '陈老师',
    time: ['周一', '16:00', '17:30'],
    description: '本课程主要介绍现代管理学的基本原理和方法，探讨管理实践中的应用。',
    scores: [7, 8, 9, 6, 5]
  },
]);

const selectedCourse = ref(null);
const radarChart = ref(null);
const currentPage = ref(1);
const itemsPerPage = ref(4);
const totalCourses = ref(courses.value.length);

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
  nextTick(() => {
    const myChart = echarts.init(radarChart.value);
    const labels = ['专业性', '生动性', '教师分', '作业适合分', '难度分'];
    const scores = selectedCourse.value.scores;
    const option = {
      title: {
        text: ''
      },
      tooltip: {},
      legend: {
        data: ['课程评分']
      },
      radar: {
        indicator: labels.map(label => ({ name: label, max: 10 })),
        shape: 'circle'
      },
      series: [{
        name: '课程评分',
        type: 'radar',
        data: [{
          value: scores,
          name: '课程评分'
        }]
      }]
    };

    myChart.setOption(option);
  });
}

// 处理页面变化
function handlePageChange(page) {
  currentPage.value = page;
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

.preference-chart {
  margin-top: 20px;
}
</style>
