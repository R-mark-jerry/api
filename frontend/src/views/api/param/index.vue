<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="API ID" prop="apiId">
        <el-input
          v-model="queryParams.apiId"
          placeholder="请输入API ID"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="参数名称" prop="paramName">
        <el-input
          v-model="queryParams.paramName"
          placeholder="请输入参数名称"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="参数类型" prop="paramType">
        <el-select v-model="queryParams.paramType" placeholder="参数类型" clearable>
          <el-option
            v-for="dict in dict.type.api_param_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="Plus"
          @click="handleAdd"
          v-hasPermi="['api:param:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['api:param:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['api:param:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Close"
          @click="handleClose"
        >关闭</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="paramList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="参数ID" align="center" prop="paramId" />
      <el-table-column label="API ID" align="center" prop="apiId" />
      <el-table-column label="参数名称" align="center" prop="paramName" />
      <el-table-column label="参数类型" align="center" prop="paramType">
        <template #default="scope">
          <dict-tag :options="dict.type.api_param_type" :value="scope.row.paramType"/>
        </template>
      </el-table-column>
      <el-table-column label="数据类型" align="center" prop="dataType">
        <template #default="scope">
          <dict-tag :options="dict.type.api_data_type" :value="scope.row.dataType"/>
        </template>
      </el-table-column>
      <el-table-column label="是否必填" align="center" prop="required">
        <template #default="scope">
          <dict-tag :options="dict.type.sys_yes_no" :value="scope.row.required"/>
        </template>
      </el-table-column>
      <el-table-column label="默认值" align="center" prop="defaultValue" />
      <el-table-column label="显示顺序" align="center" prop="orderNum" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button
            type="text"
            icon="Edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['api:param:edit']"
          >修改</el-button>
          <el-button
            type="text"
            icon="Delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['api:param:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      v-model:page="queryParams.pageNum"
      v-model:limit="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改API参数对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="paramRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="API ID" prop="apiId">
          <el-input v-model="form.apiId" placeholder="请输入API ID" />
        </el-form-item>
        <el-form-item label="参数名称" prop="paramName">
          <el-input v-model="form.paramName" placeholder="请输入参数名称" />
        </el-form-item>
        <el-form-item label="参数类型" prop="paramType">
          <el-select v-model="form.paramType" placeholder="请选择参数类型">
            <el-option
              v-for="dict in dict.type.api_param_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="数据类型" prop="dataType">
          <el-select v-model="form.dataType" placeholder="请选择数据类型">
            <el-option
              v-for="dict in dict.type.api_data_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="是否必填" prop="required">
          <el-radio-group v-model="form.required">
            <el-radio
              v-for="dict in dict.type.sys_yes_no"
              :key="dict.value"
              :label="dict.value"
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="默认值" prop="defaultValue">
          <el-input v-model="form.defaultValue" placeholder="请输入默认值" />
        </el-form-item>
        <el-form-item label="参数描述" prop="paramDescription">
          <el-input v-model="form.paramDescription" type="textarea" placeholder="请输入参数描述" />
        </el-form-item>
        <el-form-item label="显示顺序" prop="orderNum">
          <el-input-number v-model="form.orderNum" controls-position="right" :min="0" />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup name="ApiParam">
import { listParam, getParam, delParam, addParam, updateParam } from "@/api/api/param";

const { proxy } = getCurrentInstance();

const paramList = ref([]);
const open = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    apiId: null,
    paramName: null,
    paramType: null
  },
  rules: {
    apiId: [
      { required: true, message: "API ID不能为空", trigger: "blur" }
    ],
    paramName: [
      { required: true, message: "参数名称不能为空", trigger: "blur" }
    ],
    paramType: [
      { required: true, message: "参数类型不能为空", trigger: "change" }
    ],
    dataType: [
      { required: true, message: "数据类型不能为空", trigger: "change" }
    ]
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询API参数列表 */
function getList() {
  loading.value = true;
  listParam(queryParams.value).then(response => {
    paramList.value = response.rows;
    total.value = response.total;
    loading.value = false;
  });
}

/** 取消按钮 */
function cancel() {
  open.value = false;
  reset();
}

/** 表单重置 */
function reset() {
  form.value = {
    paramId: null,
    apiId: null,
    paramName: null,
    paramType: null,
    dataType: null,
    required: "0",
    defaultValue: null,
    paramDescription: null,
    orderNum: 0
  };
  proxy.resetForm("paramRef");
}

/** 搜索按钮操作 */
function handleQuery() {
  queryParams.value.pageNum = 1;
  getList();
}

/** 重置按钮操作 */
function resetQuery() {
  proxy.resetForm("queryRef");
  handleQuery();
}

/** 多选框选中数据 */
function handleSelectionChange(selection) {
  ids.value = selection.map(item => item.paramId);
  single.value = selection.length !== 1;
  multiple.value = !selection.length;
}

/** 新增按钮操作 */
function handleAdd() {
  reset();
  open.value = true;
  title.value = "添加API参数";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _paramId = row.paramId || ids.value;
  getParam(_paramId).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改API参数";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["paramRef"].validate(valid => {
    if (valid) {
      if (form.value.paramId != null) {
        updateParam(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addParam(form.value).then(response => {
          proxy.$modal.msgSuccess("新增成功");
          open.value = false;
          getList();
        });
      }
    }
  });
}

/** 删除按钮操作 */
function handleDelete(row) {
  const _paramIds = row.paramId || ids.value;
  proxy.$modal.confirm('是否确认删除API参数编号为"' + _paramIds + '"的数据项？').then(function() {
    return delParam(_paramIds);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 关闭按钮操作 */
function handleClose() {
  const obj = { path: "/api/info" };
  proxy.$tab.closeOpenPage(obj);
}

getList();
</script>