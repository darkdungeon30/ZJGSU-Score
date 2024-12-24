<template>
  <el-container class="course-list-container">
    <el-header class="course-list-header">
      <h2>课程列表</h2>
    </el-header>
    <div style="display: flex">
      <el-input
          placeholder="请输入课程名称进行搜索"
          class="search-input">
      </el-input>
      <el-button slot="append" icon="el-icon-search">搜索</el-button>
    </div>
    <el-main class="course-list-main">
      <el-container v-if="selectedCourse" class="course-details-container">
        <div ref="radarChart" style="width: 100%; height: 400px;"></div>
        <el-header class="course-details-header">
          <h2 style="text-align: center;">{{ selectedCourse.name }} - 课程均分详情</h2>
        </el-header>
        <el-form ref="courseForm" :model="selectedCourse" label-width="120px">
          <el-form-item label="课程名称">
            <el-input v-model="selectedCourse.name"></el-input>
          </el-form-item>
          <el-form-item label="开课学院">
            <el-input v-model="selectedCourse.department"></el-input>
          </el-form-item>
          <el-form-item label="上课时间">
            <el-input v-for="(time, index) in selectedCourse.time" :key="index" v-model="selectedCourse.time[index]"></el-input>
          </el-form-item>
          <el-form-item label="授课老师">
            <el-input v-model="selectedCourse.teacher"></el-input>
          </el-form-item>
          <el-form-item label="课程简介">
            <el-input type="textarea" v-model="selectedCourse.description"></el-input>
          </el-form-item>
          <el-button @click="saveAndClose">保存并关闭</el-button>
          <el-button @click="selectedCourse = null">关闭</el-button>
        </el-form>
      </el-container>

      <div class="overlay" v-if="selectedCourse"></div>

      <el-row v-for="course in paginatedCourses" :key="course.name" class="course-item" @click="showCourseDetails(course)">
        <el-col :span="18">
          <div class="course-info">
            <h3>{{ course.name }}</h3>
            <p>开课学院：{{ course.department }}</p>
            <p>任课老师：{{ course.teacher }}</p>
            <p>授课时间：{{ formatTime(course.time) }}</p>
            <p>课程简介：{{ course.description }}</p>
          </div>
        </el-col>
        <el-col :span="6" class="course-actions">
          <el-button class="delete-course-button" type="danger" @click.stop="deleteCourse(course)">删除课程</el-button>
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
const courses = ref([
  {
    name: '人工智能导论',
    teacher: '李老师',
    time: ['周二', '10:00', '11:30'],
    description: '本课程旨在介绍人工智能的基本概念、发展历程和主要技术，培养学生的创新思维和实践能力。',
    scores: [9, 8, 7, 6, 5],
    department: '计算机科学与技术学院'
  },
  {
    name: '现代经济学原理',
    teacher: '王老师',
    time: ['周三', '14:00', '15:30'],
    description: '本课程主要讲解现代经济学的基本原理和方法，分析市场经济的运行机制和政策影响。',
    scores: [7.7, 8.6, 9.1, 5.9, 6],
    department: '经济学院'
  },
  {
    name: '心理学基础',
    teacher: '赵老师',
    time: ['周四', '16:00', '17:30'],
    description: '本课程旨在帮助学生了解心理学的基础知识，包括认知、情感和行为等方面。',
    scores: [6, 5, 7, 8, 9],
    department: '教育学院'
  },
  {
    name: '环境科学概论',
    teacher: '钱老师',
    time: ['周五', '8:05', '9:35'],
    description: '本课程介绍环境科学的基本概念、原理和方法，探讨环境问题及其解决方案。',
    scores: [8, 9, 6, 7, 5],
    department: '环境科学与工程学院'
  },
  {
    name: '中国传统文化',
    teacher: '孙老师',
    time: ['周一', '14:00', '15:30'],
    description: '本课程旨在传承和弘扬中国传统文化，包括文学、艺术、哲学等多个方面。',
    scores: [7, 6, 5, 8, 9],
    department: '文学院'
  },
  {
    name: '西方哲学史',
    teacher: '周老师',
    time: ['周二', '16:00', '17:30'],
    description: '本课程主要介绍西方哲学的发展历史，探讨各个时期的哲学思想和流派。',
    scores: [5, 6, 7, 8, 9],
    department: '哲学学院'
  },
  {
    name: '健康与营养学',
    teacher: '吴老师',
    time: ['周三', '10:00', '11:30'],
    description: '本课程旨在教授健康与营养的基本知识，帮助学生建立科学的健康观念和饮食习惯。',
    scores: [9, 7, 8, 6, 5],
    department: '公共卫生学院'
  },
  {
    name: '音乐鉴赏',
    teacher: '郑老师',
    time: ['周四', '10:00', '11:30'],
    description: '本课程旨在培养学生的音乐鉴赏能力，介绍不同音乐风格和作品。',
    scores: [8, 9, 7, 6, 5],
    department: '音乐学院'
  },
  {
    name: '创新创业实践',
    teacher: '冯老师',
    time: ['周五', '14:00', '15:30'],
    description: '本课程旨在激发学生的创新创业精神，教授创业知识和实践技能。',
    scores: [6, 5, 7, 8, 9],
    department: '管理学院'
  },
  {
    name: '现代管理学',
    teacher: '陈老师',
    time: ['周一', '16:00', '17:30'],
    description: '本课程主要介绍现代管理学的基本原理和方法，探讨管理实践中的应用。',
    scores: [7, 8, 9, 6, 5],
    department: '管理学院'
  },
  {
    name: '国际关系与外交',
    teacher: '褚老师',
    time: ['周二', '8:05', '9:35'],
    description: '本课程旨在介绍国际关系的基本理论，分析国际政治和外交政策。',
    scores: [5, 6, 7, 8, 9],
    department: '国际关系学院'
  },
  {
    name: '数据科学与大数据技术',
    teacher: '卫老师',
    time: ['周三', '16:00', '17:30'],
    description: '本课程旨在介绍数据科学的基础知识和大数据技术，培养学生的数据思维和分析能力。',
    scores: [9, 8, 7, 6, 5],
    department: '信息科学与技术学院'
  }
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

.course-actions {
  display: flex;
  justify-content: center;
  align-items: center;
}

.delete-course-button {
  display: block; /* 使按钮独占整行，以便居中对齐 */
}
</style>
