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
      <el-form-item label="角色ID" prop="roleId">
        <el-input
          v-model="queryParams.roleId"
          placeholder="请输入角色ID"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="权限类型" prop="permissionType">
        <el-select v-model="queryParams.permissionType" placeholder="权限类型" clearable>
          <el-option
            v-for="dict in dict.type.api_permission_type"
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
          v-hasPermi="['api:permission:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['api:permission:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['api:permission:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="Share"
          @click="handleAssign"
          v-hasPermi="['api:permission:assign']"
        >分配权限</el-button>
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

    <el-table v-loading="loading" :data="permissionList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="权限ID" align="center" prop="permissionId" />
      <el-table-column label="API ID" align="center" prop="apiId" />
      <el-table-column label="API名称" align="center" prop="apiName" />
      <el-table-column label="角色ID" align="center" prop="roleId" />
      <el-table-column label="角色名称" align="center" prop="roleName" />
      <el-table-column label="权限类型" align="center" prop="permissionType">
        <template #default="scope">
          <dict-tag :options="dict.type.api_permission_type" :value="scope.row.permissionType"/>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button
            type="text"
            icon="Edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['api:permission:edit']"
          >修改</el-button>
          <el-button
            type="text"
            icon="Delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['api:permission:remove']"
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

    <!-- 添加或修改API权限对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="permissionRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="API ID" prop="apiId">
          <el-input v-model="form.apiId" placeholder="请输入API ID" />
        </el-form-item>
        <el-form-item label="角色ID" prop="roleId">
          <el-input v-model="form.roleId" placeholder="请输入角色ID" />
        </el-form-item>
        <el-form-item label="权限类型" prop="permissionType">
          <el-radio-group v-model="form.permissionType">
            <el-radio
              v-for="dict in dict.type.api_permission_type"
              :key="dict.value"
              :label="dict.value"
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>

    <!-- 分配权限对话框 -->
    <el-dialog title="分配API权限" v-model="assignOpen" width="500px" append-to-body>
      <el-form ref="assignRef" :model="assignForm" :rules="assignRules" label-width="80px">
        <el-form-item label="角色ID" prop="roleId">
          <el-input v-model="assignForm.roleId" placeholder="请输入角色ID" />
        </el-form-item>
        <el-form-item label="API IDs" prop="apiIds">
          <el-input
            v-model="assignForm.apiIds"
            type="textarea"
            placeholder="请输入API ID，多个用逗号分隔"
            :rows="4"
          />
        </el-form-item>
        <el-form-item label="权限类型" prop="permissionType">
          <el-radio-group v-model="assignForm.permissionType">
            <el-radio
              v-for="dict in dict.type.api_permission_type"
              :key="dict.value"
              :label="dict.value"
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitAssign">确 定</el-button>
          <el-button @click="cancelAssign">取 消</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup name="ApiPermission">
import { listPermission, getPermission, delPermission, addPermission, updatePermission, assignApiPermissionsToRole } from "@/api/api/permission";

const { proxy } = getCurrentInstance();

const permissionList = ref([]);
const open = ref(false);
const assignOpen = ref(false);
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
    roleId: null,
    permissionType: null
  },
  rules: {
    apiId: [
      { required: true, message: "API ID不能为空", trigger: "blur" }
    ],
    roleId: [
      { required: true, message: "角色ID不能为空", trigger: "blur" }
    ],
    permissionType: [
      { required: true, message: "权限类型不能为空", trigger: "change" }
    ]
  },
  assignForm: {},
  assignRules: {
    roleId: [
      { required: true, message: "角色ID不能为空", trigger: "blur" }
    ],
    apiIds: [
      { required: true, message: "API ID不能为空", trigger: "blur" }
    ],
    permissionType: [
      { required: true, message: "权限类型不能为空", trigger: "change" }
    ]
  }
});

const { queryParams, form, rules, assignForm, assignRules } = toRefs(data);

/** 查询API权限列表 */
function getList() {
  loading.value = true;
  listPermission(queryParams.value).then(response => {
    permissionList.value = response.rows;
    total.value = response.total;
    loading.value = false;
  });
}

/** 取消按钮 */
function cancel() {
  open.value = false;
  reset();
}

/** 分配权限取消按钮 */
function cancelAssign() {
  assignOpen.value = false;
  resetAssign();
}

/** 表单重置 */
function reset() {
  form.value = {
    permissionId: null,
    apiId: null,
    roleId: null,
    permissionType: "0"
  };
  proxy.resetForm("permissionRef");
}

/** 分配权限表单重置 */
function resetAssign() {
  assignForm.value = {
    roleId: null,
    apiIds: null,
    permissionType: "0"
  };
  proxy.resetForm("assignRef");
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
  ids.value = selection.map(item => item.permissionId);
  single.value = selection.length !== 1;
  multiple.value = !selection.length;
}

/** 新增按钮操作 */
function handleAdd() {
  reset();
  open.value = true;
  title.value = "添加API权限";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _permissionId = row.permissionId || ids.value;
  getPermission(_permissionId).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改API权限";
  });
}

/** 分配权限按钮操作 */
function handleAssign() {
  resetAssign();
  assignOpen.value = true;
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["permissionRef"].validate(valid => {
    if (valid) {
      if (form.value.permissionId != null) {
        updatePermission(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addPermission(form.value).then(response => {
          proxy.$modal.msgSuccess("新增成功");
          open.value = false;
          getList();
        });
      }
    }
  });
}

/** 提交分配权限按钮 */
function submitAssign() {
  proxy.$refs["assignRef"].validate(valid => {
    if (valid) {
      const apiIds = assignForm.value.apiIds.split(',').map(id => parseInt(id.trim()));
      assignApiPermissionsToRole(assignForm.value.roleId, apiIds, assignForm.value.permissionType).then(response => {
        proxy.$modal.msgSuccess("分配成功");
        assignOpen.value = false;
        getList();
      });
    }
  });
}

/** 删除按钮操作 */
function handleDelete(row) {
  const _permissionIds = row.permissionId || ids.value;
  proxy.$modal.confirm('是否确认删除API权限编号为"' + _permissionIds + '"的数据项？').then(function() {
    return delPermission(_permissionIds);
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