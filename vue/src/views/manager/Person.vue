<template>
  <div style="width: 50%">
    <div class="card">
      <el-form :model="data.user" label-width="100px" style="padding-right: 50px">
        <el-form-item label="序号">
          <el-input disabled v-model="data.user.id" autocomplete="off" />
        </el-form-item>
        <el-form-item label="头像">
          <el-upload :show-file-list="false" class="avatar-uploader" action="http://localhost:9090/files/upload" :on-success="handleFileUpload">
            <img v-if="data.user.avatar" :src="data.user.avatar" class="avatar" style="width: 50px; height: 50px; border-radius: 50%"/>
            <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
          </el-upload>
        </el-form-item>
        <el-form-item label="账号">
          <el-input disabled v-model="data.user.username" autocomplete="off" />
        </el-form-item>
        <el-form-item label="名称">
          <el-input v-model="data.user.name" autocomplete="off" />
        </el-form-item>
        <el-form-item label="性别" v-if="data.user.role === 'USER'">
          <el-radio-group v-model="data.user.sex">
            <el-radio label="男"></el-radio>
            <el-radio label="女"></el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="手机" v-if="data.user.role === 'USER'">
          <el-input v-model="data.user.phone" autocomplete="off" />
        </el-form-item>
        <el-form-item label="电话" v-if="data.user.role === 'BUSINESS'">
          <el-input v-model="data.user.phone" autocomplete="off" />
        </el-form-item>
        <el-form-item label="简介" v-if="data.user.role === 'BUSINESS'">
          <el-input v-model="data.user.info" autocomplete="off" />
        </el-form-item>
        <el-form-item label="地址" v-if="data.user.role === 'BUSINESS'">
          <el-input v-model="data.user.address" autocomplete="off" />
        </el-form-item>
        <el-form-item label="营业执照"  v-if="data.user.role === 'BUSINESS'">
          <el-upload :show-file-list="false" class="license-uploader" action="http://localhost:9090/files/upload" :on-success="handleLicenseUpload">
            <img v-if="data.user.license" :src="data.user.license" class="license" style="width: 355px; height: 200px;"/>
            <el-icon v-else class="license-uploader-icon"><Plus /></el-icon>
          </el-upload>
        </el-form-item>
        <el-form-item label="类型" v-if="data.user.role === 'BUSINESS'">
          <el-input v-model="data.user.type" autocomplete="off" />
        </el-form-item>
        <el-form-item>
          <el-button type="danger" @click="del(scope.row.id)">删除</el-button>
          <el-button type="primary" @click="save">保存</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script setup>
import { reactive } from "vue";
import request from "@/utils/request";
import { ElMessage, ElMessageBox } from "element-plus";

const data = reactive({
  user: JSON.parse(localStorage.getItem('canteen-user') || '{}'),
});
const dt = reactive({
  business: JSON.parse(localStorage.getItem('canteen-business') || '{}'),
});

const handleFileUpload = (file) => {
  data.user.avatar = file.data;
};

const handleLicenseUpload = (file) => {
  data.user.license = file.data; // 假设返回的数据包含文件路径或 URL
};

const emit = defineEmits(["updateUser"]);

const save = () => {
  if (data.user.role === 'ADMIN') {
    request.put('/admin/update', data.user).then(res => {
      if (res.code === '200') {
        ElMessage.success('更新成功');
      } else {
        ElMessage.error(res.msg);
      }
    });
  } else if (data.user.role === 'USER'){
    request.put('/user/update', data.user).then(res => {
      if (res.code === '200') {
        ElMessage.success('更新成功');
      } else {
        ElMessage.error(res.msg);
      }
    });
  } else if (data.user.role === 'BUSINESS'){
    request.put('/business/update', data.user).then(res => {
      if (res.code === '200') {
        ElMessage.success('更新成功');
      } else {
        ElMessage.error(res.msg);
      }
    });
  }
  localStorage.setItem('canteen-user', JSON.stringify(data.user));
  localStorage.setItem('canteen-business', JSON.stringify(dt.business));
  emit('updateUser');
};
</script>