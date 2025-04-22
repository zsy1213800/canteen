<template>
    <div>
      <el-upload
        :action="'http://localhost:8080/files/upload'"
        list-type="picture-card"
        :limit="1"
        :on-exceed="limitError"
        :on-success="imgSuccess"
        :on-error="imgError"
      >
        <i class="el-icon-plus"></i>
      </el-upload>
    </div>
  </template>
  
  <script>
  export default {
    data() {
      return {
        imageUrl: '',
        form: {
          image: ''
        }
      };
    },
    methods: {
      imgSuccess(res, file) {
        this.imageUrl = res.data;
        this.form.image = this.imageUrl;
        console.log(res.data);
      },
      imgError(res) {
        this.$message({
          type: "error",
          message: "附件上传失败"
        });
      },
      limitError() {
        this.$message({
          type: "error",
          message: "图片仅可上传一张"
        });
      }
    }
  };
  </script>
  