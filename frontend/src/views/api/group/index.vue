<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="分组名称">
        <el-input
          v-model="queryParams.groupName"
          placeholder="请输入分组名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态">
        <el-select v-model="queryParams.status" placeholder="分组状态" clearable>
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
          v-hasPermi="['api:group:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="el-icon-sort"
          size="mini"
          @click="toggleExpandAll"
        >展开/折叠</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['api:group:export']"
        >导出</el-button>
      </el-col>
    </el-row>

    <el-table
      v-loading="loading"
      :data="groupList"
      row-key="groupId"
      :default-expand-all="isExpandAll"
      :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
    >
      <el-table-column prop="groupName" label="分组名称" :show-overflow-tooltip="true" width="260"></el-table-column>
      <el-table-column prop="groupCode" label="分组编码" :show-overflow-tooltip="true" width="200"></el-table-column>
      <el-table-column prop="orderNum" label="显示顺序" width="100"></el-table-column>
      <el-table-column prop="status" label="状态" width="100">
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
            v-hasPermi="['api:group:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-plus"
            @click="handleAdd(scope.row)"
            v-hasPermi="['api:group:add']"
          >新增</el-button>
          <el-button
            v-if="scope.row.parentId == 0"
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['api:group:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 添加或修改分组对话框 -->
    <el-dialog :title="title" v-model="open" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="上级分组" prop="parentId">
          <el-tree-select
            v-model="form.parentId"
            :data="groupOptions"
            :props="{ value: 'groupId', label: 'groupName', children: 'children' }"
            value-key="groupId"
            placeholder="选择上级分组"
            check-strictly
          />
        </el-form-item>
        <el-form-item label="分组名称" prop="groupName">
          <el-input v-model="form.groupName" placeholder="请输入分组名称" />
        </el-form-item>
        <el-form-item label="分组编码" prop="groupCode">
          <el-input v-model="form.groupCode" placeholder="请输入分组编码" />
        </el-form-item>
        <el-form-item label="显示排序" prop="orderNum">
          <el-input-number v-model="form.orderNum" controls-position="right" :min="0" />
        </el-form-item>
        <el-form-item label="分组描述">
          <el-input v-model="form.description" type="textarea" placeholder="请输入分组描述"></el-input>
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

<script>
import { listGroup, getGroup, delGroup, addGroup, updateGroup, listGroupExcludeChild } from "@/api/api/group";
import { Treeselect } from "@/components/Treeselect";
import IconSelect from "@/components/IconSelect";

export default {
  name: "Group",
  dicts: ['sys_normal_disable'],
  components: { Treeselect, IconSelect },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 显示搜索条件
      showSearch: true,
      // 分组表格数据
      groupList: [],
      // 分组树选项
      groupOptions: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 是否展开所有
      isExpandAll: false,
      // 重新渲染表格状态
      refreshTable: true,
      // 查询参数
      queryParams: {
        groupName: undefined,
        status: undefined
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        parentId: [
          { required: true, message: "上级分组不能为空", trigger: "blur" }
        ],
        groupName: [
          { required: true, message: "分组名称不能为空", trigger: "blur" }
        ],
        groupCode: [
          { required: true, message: "分组编码不能为空", trigger: "blur" }
        ],
        orderNum: [
          { required: true, message: "显示排序不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询分组列表 */
    getList() {
      this.loading = true;
      listGroup(this.queryParams).then(response => {
        this.groupList = this.handleTree(response.data);
        this.loading = false;
      });
    },
    /** 查询分组下拉树结构 */
    getTreeselect() {
      listGroup().then(response => {
        this.groupOptions = [];
        const group = { groupId: 0, groupName: '主类目', children: [] };
        group.children = this.handleTree(response.data);
        this.groupOptions.push(group);
      });
    },
    /** 转换分组数据结构 */
    normalizer(node) {
      if (node.children && !node.children.length) {
        node.children = undefined;
      }
      return {
        id: node.groupId,
        label: node.groupName,
        children: node.children
      };
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      this.getTreeselect();
      if (row.groupId != null) {
        this.title = "修改分组";
        this.form.groupId = row.groupId;
        this.form.parentId = row.parentId;
        this.form.groupName = row.groupName;
        this.form.groupCode = row.groupCode;
        this.form.orderNum = row.orderNum;
        this.form.description = row.description;
      } else {
        this.title = "新增分组";
      }
      this.open = true;
    },
    /** 新增按钮操作 */
    handleAdd(row) {
      this.reset();
      this.getTreeselect();
      if (row != null && row.groupId) {
        this.form.parentId = row.groupId;
      } else {
        this.form.parentId = 0;
      }
      this.title = "新增分组";
      this.open = true;
    },
    /** 展开/折叠操作 */
    toggleExpandAll() {
      this.refreshTable = false;
      this.isExpandAll = !this.isExpandAll;
      this.$nextTick(() => {
        this.refreshTable = true;
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.groupId != undefined) {
            updateGroup(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addGroup(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除名称为"' + row.groupName + '"的数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(() => {
        return delGroup(row.groupId);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('api/manage/group/export', {
        ...this.queryParams
      }, `group_${new Date().getTime()}.xlsx`)
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        groupId: undefined,
        parentId: 0,
        groupName: undefined,
        groupCode: undefined,
        orderNum: 0,
        description: undefined
      };
      this.resetForm("form");
    },
    /** 构建树形结构 */
    handleTree(data) {
      const menu = { groupId: 0, groupName: '主类目', children: [] };
      menu.children = this.handleTreeData(data, 0);
      return [menu];
    },
    /** 处理树形数据 */
    handleTreeData(data, parentId) {
      let arr = [];
      data.forEach(item => {
        if (item.parentId === parentId) {
          let children = this.handleTreeData(data, item.groupId);
          if (children.length) {
            item.children = children;
          }
          arr.push(item);
        }
      });
      return arr;
    }
  }
};
</script>