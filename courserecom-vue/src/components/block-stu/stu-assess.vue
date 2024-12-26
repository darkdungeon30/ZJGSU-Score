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
          <!-- 新增评分表单 -->
          <div style="display: flex;">
          <el-form-item label="专业性">
              <el-input v-model="evaluateForm.especial" type="number" min="0" max="10"></el-input>
            </el-form-item>
            <el-form-item label="生动性">
              <el-input v-model="evaluateForm.efun" type="number" min="0" max="10"></el-input>
            </el-form-item>
            <el-form-item label="给分情况">
              <el-input v-model="evaluateForm.escore" type="number" min="0" max="10"></el-input>
            </el-form-item>
            <el-form-item label="期末难度">
              <el-input v-model="evaluateForm.eexam" type="number" min="0" max="10"></el-input>
            </el-form-item>
            <el-form-item label="难度分">
              <el-input v-model="evaluateForm.edifficult" type="number" min="0" max="10"></el-input>
            </el-form-item>
            <el-form-item label="推荐指数">
              <el-input v-model="evaluateForm.erecommend" type="number" min="0" max="10"></el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="submitEvaluate">提交评分</el-button>
            </el-form-item>
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

// 新增评分表单数据
const evaluateForm = ref({
  especial: 0,
  efun: 0,
  escore: 0,
  eexam: 0,
  edifficult: 0,
  erecommend: 0
});
const paginatedCourses = computed(() => {
  const start = (currentPage.value - 1) * itemsPerPage.value;
  const end = start + itemsPerPage.value;
  return courses.value.slice(start, end);
});

async function fetchCourses() {
  try {
    // 获取用户的课程 ID 数据
    const response = await axios.get(`http://localhost:8090/lessonuser/lesson/${claims.id}`);
    console.log("返回课程 IDs：", response.data);

    const lids = response.data;
    const coursePromises = lids.map(lid => axios.get(`http://localhost:8090/lesson/info/${lid}`));
    const courseResponses = await Promise.all(coursePromises);
    courses.value = courseResponses.map(res => res.data);
    totalCourses.value = courses.value.length;
    console.log("课程信息：", courses.value); // 输出获取到的课程信息
  } catch (error) {
    console.error('获取课程数据失败:', error);
  }
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

function calcCourseScores(evaluates) {
  const scores = evaluates;
  const scoreKeys = ['especial', 'efun', 'escore', 'eexam', 'edifficult', 'erecommend'];
  const labels = ['专业性', '生动性', '给分情况', '期末难度', '难度分', '推荐指数'];
  return scoreKeys.map((key, index) => {
    const scoresForItem = scores.map(evaluate => parseFloat(evaluate[key].toFixed(2)));
    return {
      name: labels[index], // 使用中文标签
      value: scoresForItem.reduce((sum, score) => sum + score, 0) / scores.length
    };
  });
}

async function showCourseDetails(course) {
  selectedCourse.value = course;
  const evaluates = await fetchEvaluates(course);
  const scoreData = calcCourseScores(evaluates).map(item => ({
    ...item,
    value: parseFloat(item.value.toFixed(2))
  }));

  const userEvaluates = await fetchUserEvaluates(course);
  const userScoreData = calcCourseScores(userEvaluates).map(item => ({
    ...item,
    value: parseFloat(item.value.toFixed(2))
  }));

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

    const myChartStu = echarts.init(radarChartStu.value);
    const optionStu = {
      tooltip: {},
      legend: {
        data: ['用户评分']
      },
      radar: {
        indicator: userScoreData.filter(item => item.name !== '推荐指数').map(item => ({
          name: item.name,
          max: 10
        }))
      },
      series: [{
        name: '用户评分',
        type: 'radar',
        data: [{
          value: userScoreData.filter(item => item.name !== '推荐指数').map(item => item.value),
          name: '用户评分'
        }]
      }]
    };

    myChartStu.setOption(optionStu);
  });
}

async function fetchUserEvaluates(course) {
  try {
    const response = await axios.post(`http://localhost:8090/evaluate/search`, {
      uid: claims.id,
      lid: course.lid
    });
    if (!response.data) {
      throw new Error("No data received");
    }
    console.log("返回用户评价：", response.data);
    // 假设response.data中包含了用户评分数据
    if (response.data.length > 0) {
      // 将用户评分赋值给evaluateForm
      // evaluateForm.value.especial = response.data[0].especial;
      // evaluateForm.value.efun = response.data[0].efun;
      // evaluateForm.value.escore = response.data[0].escore;
      // evaluateForm.value.eexam = response.data[0].eexam;
      // evaluateForm.value.edifficult = response.data[0].edifficult;
      // evaluateForm.value.erecommend = response.data[0].erecommend;
    }
    return response.data;
  } catch (error) {
    console.error('Fetching user evaluates failed:', error);
    ElMessage.error("获取用户评分详情失败，请检查网络或联系管理员！");
    return [];
  }
}

function handlePageChange(page) {
  currentPage.value = page;
}
// 新增提交评分函数
async function submitEvaluate() {
  try {
    const userEvaluates = await fetchUserEvaluates(selectedCourse.value);
    console.log("eid:",userEvaluates[0].eid);
    if (userEvaluates.length > 0) {
      const evaluate = {
        eid: userEvaluates[0].eid,
        especial: evaluateForm.value.especial || null,
        efun: evaluateForm.value.efun || null,
        escore: evaluateForm.value.escore || null,
        eexam: evaluateForm.value.eexam || null,
        edifficult: evaluateForm.value.edifficult || null,
        erecommend: evaluateForm.value.erecommend || null
      };
      console.log("evaluate:",evaluate)
      if(claims.type === 2){
        ElMessage.error("您已被封禁，无法评分！请联系管理员！")
      }else{
        const response = await axios.post('http://localhost:8090/evaluate/update', evaluate);
        if (response.data) {
          ElMessage.success('评分提交成功！');
          // 重新获取用户评分并更新雷达图
          await showCourseDetails(selectedCourse.value);
        } else {
          ElMessage.error('评分提交失败，请重试！');
        }
      }
    } else {
      ElMessage.error('未找到用户评分详情，无法获取eid！');
    }
  } catch (error) {
    console.error('提交评分失败:', error);
    ElMessage.error('提交评分失败，请检查网络或联系管理员！');
  }
}
onMounted(async () => {
  await fetchCourses();
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
  height: 1000px;
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
