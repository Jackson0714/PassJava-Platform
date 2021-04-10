<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="150px">
      <el-form-item label="类型名称" prop="type">
        <el-input v-model="dataForm.type" placeholder="类型名称"></el-input>
      </el-form-item>
      <el-form-item label="备注" prop="comments">
        <el-input v-model="dataForm.comments" placeholder="备注"></el-input>
      </el-form-item>
      <el-form-item label="类型logo路径" prop="logoUrl">
        <single-upload v-model="dataForm.logoUrl"></single-upload>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  import SingleUpload from "@/components/upload/singleUpload"

  export default {
    data () {
      return {
        visible: false,
        dataForm: {
          id: 0,
          type: '',
          comments: '',
          logoUrl: '',
          createTime: '',
          updateTime: ''
        },
        dataRule: {
          type: [
            { required: true, message: '类型名称不能为空', trigger: 'blur' }
          ],
          comments: [
            { required: true, message: '备注不能为空', trigger: 'blur' }
          ],
          logoUrl: [
            { required: true, message: '类型logo路径不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/question/type/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.type = data.type.type
                this.dataForm.comments = data.type.comments
                this.dataForm.logoUrl = data.type.logoUrl
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
              url: this.$http.adornUrl(`/question/type/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'type': this.dataForm.type,
                'comments': this.dataForm.comments,
                'logoUrl': this.dataForm.logoUrl
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
    },
    components:{ SingleUpload }
  }
</script>
