<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible"
    @closed="dialogClose"
  >
    <el-form
      :model="dataForm"
      :rules="dataRule"
      ref="dataForm"
      @keyup.enter.native="dataFormSubmit()"
      label-width="150px"
    >
      <el-form-item label="题目标题" prop="title">
        <el-input v-model="dataForm.title" placeholder="题目标题"></el-input>
      </el-form-item>
      <el-form-item label="题目解答" prop="answer">
        <el-input type="textarea" :rows="5" v-model="dataForm.answer" placeholder="题目解答"></el-input>
      </el-form-item>
      <el-form-item label="题目难度等级" prop="level">
        <el-input v-model.number="dataForm.level" placeholder="题目难度等级"></el-input>
      </el-form-item>
      <el-form-item label="排序" prop="displayOrder">
        <el-input v-model.number="dataForm.displayOrder" placeholder="排序"></el-input>
      </el-form-item>
      <el-form-item label="副标题" prop="subTitle">
        <el-input v-model="dataForm.subTitle" placeholder="副标题"></el-input>
      </el-form-item>
      <el-form-item label="题目类型" prop="type">
        <el-select v-model="dataForm.type" placeholder="请选择" @change="chooseType">
          <el-option v-for="item in types" :key="item.id" :label="item.comments" :value="item.id">
            <span style="float: left">{{ item.comments }}</span>
            <span style="float: right; color: #8492a6; font-size: 13px">{{ item.type }}</span>
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="是否显示" prop="enable">
        <el-switch
          v-model="dataForm.enable"
          :active-value="1"
          :inactive-value="0"
          active-color="#13ce66"
          inactive-color="#ff4949"
        ></el-switch>
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
  data() {
    return {
      types: [
        {
          id: 1,
          type: "javaBasic",
          comments: "Java基础"
        },
        {
          id: 2,
          type: "jvm",
          comments: "jvm虚拟机"
        },
        {
          id: 3,
          type: "spring",
          comments: "Spring核心原理"
        },
        {
          id: 4,
          type: "bigData",
          comments: "大数据"
        },
        {
          id: 5,
          type: "thread",
          comments: "多线程"
        }
      ],
      value: "",
      key: "",
      visible: false,
      dataForm: {
        id: 0,
        title: "",
        answer: "",
        level: "",
        displayOrder: "",
        subTitle: "",
        type: "",
        key: 0,
        enable: "",
        createTime: "",
        updateTime: ""
      },
      dataRule: {
        title: [
          { required: true, message: "题目标题不能为空", trigger: "blur" }
        ],
        answer: [
          { required: true, message: "题目解答不能为空", trigger: "blur" }
        ],
        level: [
          {
            validator: (rule, value, callback) => {
              if (value == "") {
                callback(new Error("排序字段必须填写"));
              } else if (!Number.isInteger(value) || value < 0) {
                callback(new Error("排序必须是一个大于等于0的整数"));
              } else {
                callback();
              }
            },
            trigger: "blur"
          }
        ],
        displayOrder: [
          {
            validator: (rule, value, callback) => {
              if (value == "") {
                callback(new Error("排序字段必须填写"));
              } else if (!Number.isInteger(value) || value < 0) {
                callback(new Error("排序必须是一个大于等于0的整数"));
              } else {
                callback();
              }
            },
            trigger: "blur"
          }
        ],
        subTitle: [
          { required: true, message: "副标题不能为空", trigger: "blur" }
        ],
        type: [
          { required: true, message: "题目类型不能为空", trigger: "blur" }
        ],
        enable: [
          { required: true, message: "是否显示不能为空", trigger: "blur" }
        ]
      }
    };
  },
  methods: {
    dialogClose() {
      this.dataForm.type = ''
    },
    chooseType(e) {
      console.log(e);
    },
    init(id) {
      this.dataForm.id = id || 0;
      this.visible = true;
      this.$nextTick(() => {
        this.$refs["dataForm"].resetFields();
        if (this.dataForm.id) {
          this.$http({
            url: this.$http.adornUrl(
              `/question/v1/admin/question/info/${this.dataForm.id}`
            ),
            method: "get",
            params: this.$http.adornParams()
          }).then(({ data }) => {
            if (data && data.code === 0) {
              this.dataForm.title = data.question.title;
              this.dataForm.answer = data.question.answer;
              this.dataForm.level = data.question.level;
              this.dataForm.displayOrder = data.question.displayOrder;
              this.dataForm.subTitle = data.question.subTitle;
              this.dataForm.type = data.question.type;
              this.dataForm.enable = data.question.enable;
              this.dataForm.createTime = data.question.createTime;
              this.dataForm.updateTime = data.question.updateTime;
            }
          });
        }
      });
    },
    // 表单提交
    dataFormSubmit() {
      this.$refs["dataForm"].validate(valid => {
        if (valid) {
          this.$http({
            url: this.$http.adornUrl(
              `/question/v1/admin/question/${
                !this.dataForm.id ? "save" : "update"
              }`
            ),
            method: "post",
            data: this.$http.adornData({
              id: this.dataForm.id || undefined,
              title: this.dataForm.title,
              answer: this.dataForm.answer,
              level: this.dataForm.level,
              displayOrder: this.dataForm.displayOrder,
              subTitle: this.dataForm.subTitle,
              type: this.dataForm.type,
              enable: Number(this.dataForm.enable),
              createTime: this.dataForm.createTime,
              updateTime: this.dataForm.updateTime
            })
          }).then(({ data }) => {
            if (data && data.code === 0) {
              this.$message({
                message: "操作成功",
                type: "success",
                duration: 1500,
                onClose: () => {
                  this.visible = false;
                  this.$emit("refreshDataList");
                }
              });
            } else {
              this.$message.error(data.msg);
            }
          });
        }
      });
    }
  }
};
</script>
