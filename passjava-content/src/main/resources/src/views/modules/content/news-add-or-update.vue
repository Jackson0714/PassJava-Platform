<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="图片路径" prop="imageUrl">
      <el-input v-model="dataForm.imageUrl" placeholder="图片路径"></el-input>
    </el-form-item>
    <el-form-item label="标题" prop="title">
      <el-input v-model="dataForm.title" placeholder="标题"></el-input>
    </el-form-item>
    <el-form-item label="排序" prop="displayOrder">
      <el-input v-model="dataForm.displayOrder" placeholder="排序"></el-input>
    </el-form-item>
    <el-form-item label="跳转路径" prop="renderUrl">
      <el-input v-model="dataForm.renderUrl" placeholder="跳转路径"></el-input>
    </el-form-item>
    <el-form-item label="是否显示" prop="enable">
      <el-input v-model="dataForm.enable" placeholder="是否显示"></el-input>
    </el-form-item>
    <el-form-item label="创建时间" prop="createTime">
      <el-input v-model="dataForm.createTime" placeholder="创建时间"></el-input>
    </el-form-item>
    <el-form-item label="更新时间" prop="updateTime">
      <el-input v-model="dataForm.updateTime" placeholder="更新时间"></el-input>
    </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  export default {
    data () {
      return {
        visible: false,
        dataForm: {
          id: 0,
          imageUrl: '',
          title: '',
          displayOrder: '',
          renderUrl: '',
          enable: '',
          createTime: '',
          updateTime: ''
        },
        dataRule: {
          imageUrl: [
            { required: true, message: '图片路径不能为空', trigger: 'blur' }
          ],
          title: [
            { required: true, message: '标题不能为空', trigger: 'blur' }
          ],
          displayOrder: [
            { required: true, message: '排序不能为空', trigger: 'blur' }
          ],
          renderUrl: [
            { required: true, message: '跳转路径不能为空', trigger: 'blur' }
          ],
          enable: [
            { required: true, message: '是否显示不能为空', trigger: 'blur' }
          ],
          createTime: [
            { required: true, message: '创建时间不能为空', trigger: 'blur' }
          ],
          updateTime: [
            { required: true, message: '更新时间不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.id = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.id) {
            this.$http({
              url: this.$http.adornUrl(`/content/news/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.imageUrl = data.news.imageUrl
                this.dataForm.title = data.news.title
                this.dataForm.displayOrder = data.news.displayOrder
                this.dataForm.renderUrl = data.news.renderUrl
                this.dataForm.enable = data.news.enable
                this.dataForm.createTime = data.news.createTime
                this.dataForm.updateTime = data.news.updateTime
              }
            })
          }
        })
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/content/news/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'imageUrl': this.dataForm.imageUrl,
                'title': this.dataForm.title,
                'displayOrder': this.dataForm.displayOrder,
                'renderUrl': this.dataForm.renderUrl,
                'enable': this.dataForm.enable,
                'createTime': this.dataForm.createTime,
                'updateTime': this.dataForm.updateTime
              })
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.$message({
                  message: '操作成功',
                  type: 'success',
                  duration: 1500,
                  onClose: () => {
                    this.visible = false
                    this.$emit('refreshDataList')
                  }
                })
              } else {
                this.$message.error(data.msg)
              }
            })
          }
        })
      }
    }
  }
</script>
