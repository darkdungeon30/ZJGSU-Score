<template>
  <div class="app-container">
    <el-container class="course-list-container">
      <el-header class="course-list-header">
        <h2>课程列表</h2>
      </el-header>
      <div style="display: flex">
        <el-input
            v-model="search"
            type="text"
            placeholder="请输入课程名称进行搜索"
            class="search-input">
        </el-input>
        <el-button slot="append" icon="el-icon-search" @click="searchCourses">搜索</el-button>
      </div>
      <el-main class="course-list-main">
        <el-container v-if="selectedCourse" class="course-details-container">
          <div ref="radarChart" style="width: 100%; height: 400px;"></div>
          <el-header class="course-details-header">
            <h2 style="text-align: center;">{{ selectedCourse.lname }} - 课程均分详情</h2>
          </el-header>
          <el-form ref="courseForm" :model="selectedCourse" label-width="120px">
            <el-form-item label="课程名称">
              <el-input v-model="selectedCourse.lname" :disabled="true"></el-input>
            </el-form-item>
            <el-form-item label="授课老师">
              <el-input v-model="selectedCourse.lteacher"></el-input>
            </el-form-item>
            <el-form-item label="课程简介">
              <el-input type="textarea" v-model="selectedCourse.ldesc"></el-input>
            </el-form-item>
            <el-button @click="saveAndClose">保存并关闭</el-button>
            <el-button @click="selectedCourse = null">关闭</el-button>
          </el-form>
        </el-container>

        <div class="overlay" v-if="selectedCourse"></div>

        <el-row v-for="course in paginatedCourses" :key="course.name" class="course-item">
          <el-col :span="18">
            <div class="course-info">
              <h3>{{ course.lname }}</h3>
              <p>任课老师：{{ course.lteacher }}</p>
              <p>课程简介：{{ course.ldesc }}</p>
            </div>
          </el-col>
          <el-col :span="6" class="course-actions">
            <div style="display: flex; flex-direction: column;text-align: center">
              <el-button type="primary" @click="showCourseDetails(course)" style="width: 100px;margin: 10px;">编辑课程</el-button>
              <el-button type="danger" @click.stop="deleteCourse(course)" style="width: 100px;margin: 10px;">删除课程</el-button>
            </div>
          </el-col>
        </el-row>
      </el-main>
    </el-container>
    <el-pagination
        v-if="totalCourses > itemsPerPage"
        :current-page="currentPage"
        :page-size="itemsPerPage"
        :total="totalCourses"
        @current-change="handlePageChange"
        layout="total, prev, pager, next"
        style="margin-top: 20px; text-align: center;"
        class="pagination-container"
    />
  </div>
</template>

<script setup>
import {computed, nextTick, onMounted, ref} from 'vue';
import * as echarts from 'echarts';
import axios from "axios";
import {ElMessage} from "element-plus";

// 响应式变量
const search = ref('');
const searchQuery = ref('');
const courses = ref([]);
const selectedCourse = ref(null);
const radarChart = ref(null);
const currentPage = ref(1);
const itemsPerPage = ref(4);
const totalCourses = ref(0);

// 计算分页课程，包括搜索过滤
const paginatedCourses = computed(() => {
  const filteredCourses = courses.value.filter(course =>
      course.lname.toLowerCase().includes(searchQuery.value.toLowerCase())
  );

  // 更新总数为过滤后的课程数量
  totalCourses.value = filteredCourses.length;

  return filteredCourses.slice((currentPage.value - 1) * itemsPerPage.value, currentPage.value * itemsPerPage.value);
});

// 搜索课程
function searchCourses() {
  searchQuery.value = search.value;
  currentPage.value = 1; // 重置当前页为第一页
  // 这里不需要调用 fetchCourses()，因为 paginatedCourses 会自动更新
}
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
function calculateCourseScores(evaluates) {
  const scores = evaluates;
  const scoreKeys = ['especial', 'escore', 'edifficult', 'eexam', 'efun','erecommend'];
  return scoreKeys.map(key => {
    const scoresForItem = scores.map(evaluate => evaluate[key]);
    return {
      name: key,
      value: scoresForItem.reduce((sum, score) => sum + score, 0) / scores.length
    };
  });
}
// 显示课程详情和五维图
// 显示课程详情和五维图
async function showCourseDetails(course) {
  selectedCourse.value = course;
  const evaluates = await fetchEvaluates(course);
  const scoreData = calculateCourseScores(evaluates);
  await nextTick(() => {
    const myChart = echarts.init(radarChart.value);
    const option = {
      tooltip: {},
      legend: {
        data: ['课程评分']
      },
      radar: {
        name: {
          textStyle: {
            color: '#fff',
            backgroundColor: '#999',
            borderRadius: 3,
            padding: [3, 5]
          }
        },
        indicator: scoreData.filter(item => item.name !== 'erecommend').map(item => ({
          name: item.name,
          max: 10
        }))
      },
      series: [{
        name: '课程评分',
        type: 'radar',
        data: [
          {
            value: scoreData.filter(item => item.name !== 'erecommend').map(item => item.value),
            name: '课程评分'
          }
        ]
      }]
    };

    // 添加综合评价的文本
    const erecommendValue = scoreData.find(item => item.name === 'erecommend').value;
    option.title = [{
      text: '综合评价：' + erecommendValue.toFixed(2),
      left: 'right',
      top: 20,
      textStyle: {
        color: '#333',
        fontSize: 16
      }
    }];

    myChart.setOption(option);
  });
}

// 处理页面变化
function handlePageChange(page) {
  currentPage.value = page;
}

async function saveAndClose() {
  if (!selectedCourse.value) {
    ElMessage.error("请先选择一个课程进行编辑！");
    return;
  } else if (selectedCourse.value.lteacher === '' || selectedCourse.value.ldesc === '') {
    ElMessage.error("教师或简介不可为空！");
    fetchCourses();
    return;
  }
  try {
    // 将选中的课程信息发送到后端进行更新
    const response = await axios.post('http://localhost:8090/lesson/update', selectedCourse.value);
    if (response.data) {
      ElMessage.success("课程信息更新成功！");
      // 更新成功后重新获取课程列表
      fetchCourses();
      // 清空选中的课程
      selectedCourse.value = null;
    } else {
      ElMessage.error("课程信息更新失败，请重试！");
    }
  } catch (error) {
    console.error('Error updating course:', error);
    ElMessage.error("课程信息更新失败，请检查网络或联系管理员！");
  }
}

async function deleteCourse(course) {
  try {
    const response = await axios.post(`http://localhost:8090/lesson/delete/${course.lid}`);
    if (response.data) {
      ElMessage.success("删除成功！");
      fetchCourses()
    } else {
      ElMessage.error("删除失败，请重试！");
    }
  } catch (error) {
    console.error('Error:', error);
    ElMessage.error("提交失败，请检查网络或联系管理员！");
  }
}

// 获取课程信息
async function fetchCourses() {
  try {
    const response = await axios.get('http://localhost:8090/lesson/list');
    if (!response.data) {
      throw new Error("No data received");
    }
    courses.value = response.data;
    totalCourses.value = courses.value.length; // 更新总数
  } catch (error) {
    console.error('Fetching courses failed:', error);
  }
}

// 在组件挂载后获取课程信息
onMounted(fetchCourses);
</script>

<style scoped>
.app-container {
  display: flex;
  flex-direction: column;
  min-height: 100vh; /* 确保容器至少占满整个视口高度 */
}

.course-list-container {
  flex: 1; /* 使课程列表容器占据剩余空间 */
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

.pagination-container {
  margin-top: auto; /* 将分页栏推到底部 */
  padding: 20px;
  text-align: center;
}
</style>
