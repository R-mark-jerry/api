<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="API名称">
        <el-input
          v-model="queryParams.apiName"
          placeholder="请输入API名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="API路径">
        <el-input
          v-model="queryParams.apiPath"
          placeholder="请输入API路径"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="请求方法">
        <el-select v-model="queryParams.httpMethod" placeholder="请求方法" clearable>
          <el-option label="GET" value="GET" />
          <el-option label="POST" value="POST" />
          <el-option label="PUT" value="PUT" />
          <el-option label="DELETE" value="DELETE" />
          <el-option label="PATCH" value="PATCH" />
        </el-select>
      </el-form-item>
      <el-form-item label="状态">
        <el-select v-model="queryParams.status" placeholder="API状态" clearable>
          <el-option
            v-for="dict in dict.type.sys_normal_disable"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['api:info:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['api:info:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['api:info:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['api:info:export']"
        >导出</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="infoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="API名称" align="center" prop="apiName" :show-overflow-tooltip="true" />
      <el-table-column label="API路径" align="center" prop="apiPath" :show-overflow-tooltip="true" />
      <el-table-column label="请求方法" align="center" prop="httpMethod" width="100">
        <template #default="scope">
          <el-tag :type="getMethodTagType(scope.row.httpMethod)">{{ scope.row.httpMethod }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="API分组" align="center" prop="groupName" :show-overflow-tooltip="true" />
      <el-table-column label="状态" align="center" prop="status" width="100">
        <template #default="scope">
          <dict-tag :options="dict.type.sys_normal_disable" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" width="160">
        <template #default="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['api:info:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleView(scope.row)"
            v-hasPermi="['api:info:query']"
          >查看</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['api:info:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改API信息对话框 -->
    <el-dialog :title="title" v-model="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="API名称" prop="apiName">
              <el-input v-model="form.apiName" placeholder="请输入API名称" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="API分组" prop="groupId">
              <el-select v-model="form.groupId" placeholder="请选择API分组">
                <el-option
                  v-for="item in groupOptions"
                  :key="item.groupId"
                  :label="item.groupName"
                  :value="item.groupId"
                />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="API路径" prop="apiPath">
              <el-input v-model="form.apiPath" placeholder="请输入API路径" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="请求方法" prop="httpMethod">
              <el-select v-model="form.httpMethod" placeholder="请选择请求方法">
                <el-option label="GET" value="GET" />
                <el-option label="POST" value="POST" />
                <el-option label="PUT" value="PUT" />
                <el-option label="DELETE" value="DELETE" />
                <el-option label="PATCH" value="PATCH" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="API描述">
              <el-input v-model="form.description" type="textarea" placeholder="请输入API描述"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="请求示例">
              <el-input v-model="form.requestExample" type="textarea" placeholder="请输入请求示例"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="响应示例">
              <el-input v-model="form.responseExample" type="textarea" placeholder="请输入响应示例"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="状态">
              <el-radio-group v-model="form.status">
                <el-radio
                  v-for="dict in dict.type.sys_normal_disable"
                  :key="dict.value"
                  :label="dict.value"
                >{{dict.label}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="是否需要认证">
              <el-radio-group v-model="form.isAuth">
                <el-radio :label="true">是</el-radio>
                <el-radio :label="false">否</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>

    <!-- API详情对话框 -->
    <el-dialog title="API详情" v-model="viewOpen" width="800px" append-to-body>
      <el-descriptions :column="2" border>
        <el-descriptions-item label="API名称">{{ viewForm.apiName }}</el-descriptions-item>
        <el-descriptions-item label="API分组">{{ viewForm.groupName }}</el-descriptions-item>
        <el-descriptions-item label="API路径">{{ viewForm.apiPath }}</el-descriptions-item>
        <el-descriptions-item label="请求方法">
          <el-tag :type="getMethodTagType(viewForm.httpMethod)">{{ viewForm.httpMethod }}</el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="状态">
          <dict-tag :options="dict.type.sys_normal_disable" :value="viewForm.status"/>
        </el-descriptions-item>
        <el-descriptions-item label="是否需要认证">{{ viewForm.isAuth ? '是' : '否' }}</el-descriptions-item>
        <el-descriptions-item label="API描述" :span="2">{{ viewForm.description }}</el-descriptions-item>
        <el-descriptions-item label="请求示例" :span="2">
          <pre>{{ viewForm.requestExample }}</pre>
        </el-descriptions-item>
        <el-descriptions-item label="响应示例" :span="2">
          <pre>{{ viewForm.responseExample }}</pre>
        </el-descriptions-item>
      </el-descriptions>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="viewOpen = false">关 闭</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import { listInfo, getInfo, delInfo, addInfo, updateInfo } from "@/api/api/info";
import { listGroup } from "@/api/api/group";

export default {
  name: "Info",
  dicts: ['sys_normal_disable'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // API信息表格数据
      infoList: [],
      // 分组选项
      groupOptions: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 是否显示查看弹出层
      viewOpen: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        apiName: undefined,
        apiPath: undefined,
        httpMethod: undefined,
        status: undefined
      },
      // 表单参数
      form: {},
      // 查看表单参数
      viewForm: {},
      // 表单校验
      rules: {
        apiName: [
          { required: true, message: "API名称不能为空", trigger: "blur" }
        ],
        groupId: [
          { required: true, message: "API分组不能为空", trigger: "change" }
        ],
        apiPath: [
          { required: true, message: "API路径不能为空", trigger: "blur" }
        ],
        httpMethod: [
          { required: true, message: "请求方法不能为空", trigger: "change" }
        ]
      }
    };
  },
  created() {
    this.getList();
    this.getGroupOptions();
  },
  methods: {
    /** 查询API信息列表 */
    getList() {
      this.loading = true;
      listInfo(this.queryParams).then(response => {
        this.infoList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 查询分组选项 */
    getGroupOptions() {
      listGroup().then(response => {
        this.groupOptions = response.data;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        apiId: undefined,
        apiName: undefined,
        groupId: undefined,
        apiPath: undefined,
        httpMethod: undefined,
        description: undefined,
        requestExample: undefined,
        responseExample: undefined,
        status: "0",
        isAuth: true
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.apiId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加API信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const apiId = row.apiId || this.ids
      getInfo(apiId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改API信息";
      });
    },
    /** 查看按钮操作 */
    handleView(row) {
      const apiId = row.apiId;
      getInfo(apiId).then(response => {
        this.viewForm = response.data;
        this.viewOpen = true;
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.apiId != undefined) {
            updateInfo(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addInfo(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const apiIds = row.apiId || this.ids;
      this.$modal.confirm('是否确认删除API信息编号为"' + apiIds + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(() => {
          return delInfo(apiIds);
        }).then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('api/manage/info/export', {
        ...this.queryParams
      }, `info_${new Date().getTime()}.xlsx`)
    },
    /** 获取请求方法标签类型 */
    getMethodTagType(method) {
      const typeMap = {
        'GET': 'success',
        'POST': 'primary',
        'PUT': 'warning',
        'DELETE': 'danger',
        'PATCH': 'info'
      };
      return typeMap[method] || '';
    }
  }
};
</script>