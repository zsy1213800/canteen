<template>
  <div>
    <!-- 显示订单按钮 -->
    <el-button type="primary" @click="showOrderList">显示订单</el-button>
  </div>

    <!-- 显示商家及餐品 -->
  <div>
    <el-row :gutter="10">
      
      <el-col :span="6" v-for="(item, index) in data.foodsList" :key="item.id">
        
        <div class="card">
          <!-- 商家名称，竖着显示 -->
          
          <img :src="item.img" alt="" style="width: 100%; height: 280px">
          
          <div style="margin: 5px; color: #000; font-size: 18px; display: flex; align-items: center">
            <div style="flex: 1">{{ item.name }}</div>
            <div style="color: red; font-weight: bold">￥{{ item.price }}</div>
          </div>
          
          <div style="margin: 5px; color: #666">
            <el-tooltip :content="item.descr" placement="bottom" effect="customized" v-if="item.descr.length >= 20">
              <div class="line1">{{ item.descr }}</div>
            </el-tooltip>
            <div v-else>{{ item.descr }}</div>
          </div>
          
          <div style="margin: 10px 0; text-align: right">
            <el-input-number :min="1" v-model="item.num" style="margin-right: 5px"></el-input-number>
            <el-button type="primary" @click="addOrder(item)">点餐</el-button>
          </div>
        </div>
      </el-col>
    </el-row>

    <!-- 已点餐品对话框 -->
    <el-dialog v-model="data.dialogShow" title="已点餐品" width="800">
      <el-table :data="data.orderList">
        <el-table-column label="餐品图片">
          <template #default="scope">
            <el-image style="width: 50px; height: 50px" :src="scope.row.img" :preview-src-list="[scope.row.img]" preview-teleported></el-image>
          </template>
        </el-table-column>
        <el-table-column prop="name" label="餐品名称" />
        <el-table-column prop="price" label="餐品价格" />
        <el-table-column label="餐品数量">
          <template #default="scope">
            <!-- 设置输入框文本居中对齐 -->
             <el-input-number v-model="scope.row.num" :min="1" style="width: 80px; text-align: center;" @change="updateOrder(scope.row)" />
          </template>
        </el-table-column>
        <el-table-column label="操作">
          <template #default="scope">
            <el-button type="danger" @click="removeOrder(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div style="text-align: right; color: red; font-weight: bold; font-size: 20px; margin-top: 10px">总价：￥{{ data.orderTotal }}</div>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="data.dialogShow = false">关闭</el-button>
          <el-button type="primary" @click="save">下单</el-button>
        </div>
      </template>
    </el-dialog>

  </div>
</template>

<script setup>
import { reactive } from "vue";
import request from "@/utils/request";
import { ElMessage } from "element-plus";

const data = reactive({
  user: JSON.parse(localStorage.getItem('canteen-user') || '{}'),
  foodsList: [],
  dialogShow: false,
  orderList: [],
  orderTotal: 0
})

// 加载食物列表
const loadFoods = () => {
  request.get('/foods/selectAll').then(res => {
    data.foodsList = res.data || [];
    data.foodsList.forEach(item => item.num = 1);  // 设置每个餐品的默认数量为 1
  })
}
loadFoods()

// 显示订单列表对话框
const showOrderList = () => {
  data.dialogShow = true;
}

// 点餐的逻辑
const addOrder = (item) => {
  let existingItem = data.orderList.find(orderItem => orderItem.id === item.id);  // 查找是否已经有该餐品
  if (existingItem) {
    existingItem.num += item.num;  // 如果已有该餐品，增加数量
  } else {
    data.orderList.push({ ...item });  // 如果没有该餐品，添加到订单
  }

  // 计算总价
  data.orderTotal = data.orderList.reduce((total, orderItem) => total + orderItem.price * orderItem.num, 0);
  ElMessage.success('点餐成功');
}

// 删除订单中的餐品
const removeOrder = (item) => {
  const index = data.orderList.findIndex(orderItem => orderItem.id === item.id);
  if (index !== -1) {
    data.orderList.splice(index, 1);  // 从订单中移除该餐品
    // 重新计算总价
    data.orderTotal = data.orderList.reduce((total, orderItem) => total + orderItem.price * orderItem.num, 0);
    ElMessage.success('餐品已删除');
  }
}

// 修改订单中的餐品数量
const updateOrder = (item) => {
  // 如果数量为 0 或者负数，将其设置为 1，防止出现非法值
  if (item.num <= 0) {
    item.num = 1;
  }
  // 重新计算总价
  data.orderTotal = data.orderList.reduce((total, orderItem) => total + orderItem.price * orderItem.num, 0);
  ElMessage.success('餐品数量已更新');
}

// 下单的逻辑
const save = () => {
  if (data.orderList.length === 0) {
    ElMessage.warning('请选择餐品');
    return;
  }
  
  let content = data.orderList.map(item => `${item.name} x ${item.num}`).join('，');  // 生成订单内容字符串
  let orderData = {
    content: content,
    total: data.orderTotal,
    userId: data.user.id,
    status: '待出餐'
  };

  // 提交订单
  request.post('/orders/add', orderData).then(res => {
    if (res.code === '200') {
      ElMessage.success('下单成功，请在我的订单里查看订单状态');
      data.dialogShow = false;  // 关闭对话框
    } else {
      ElMessage.error(res.msg);
    }
  });
}
</script>

<style scoped>
/* 商家名称竖着显示，确保不倒着 */
.business-name {
  font-size: 20px;
  font-weight: bold;
  color: #333;
  margin: 10px 0;
}
</style>

