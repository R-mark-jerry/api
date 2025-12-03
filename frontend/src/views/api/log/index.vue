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
      <el-form-item label="请求方法">
        <el-select v-model="queryParams.httpMethod" placeholder="请求方法" clearable>
          <el-option label="GET" value="GET" />
          <el-option label="POST" value="POST" />
          <el-option label="PUT" value="PUT" />
          <el-option label="DELETE" value="DELETE" />
          <el-option label="PATCH" value="PATCH" />
        </el-select>
      </el-form-item>
      <el-form-item label="调用状态">
        <el-select v-model="queryParams.callStatus" placeholder="调用状态" clearable>
          <el-option label="成功" value="0" />
          <el-option label="失败" value="1" />
        </el-select>
      </el-form-item>
      <el-form-item label="调用时间">
        <el-date-picker
          v-model="dateRange"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['api:log:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['api:log:export']"
        >导出</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          @click="handleClean"
          v-hasPermi="['api:log:clean']"
        >清空</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="logList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="API名称" align="center" prop="apiName" :show-overflow-tooltip="true" />
      <el-table-column label="API路径" align="center" prop="apiPath" :show-overflow-tooltip="true" />
      <el-table-column label="请求方法" align="center" prop="httpMethod" width="100">
        <template #default="scope">
          <el-tag :type="getMethodTagType(scope.row.httpMethod)">{{ scope.row.httpMethod }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="调用状态" align="center" prop="callStatus" width="100">
        <template #default="scope">
          <el-tag :type="scope.row.callStatus === '0' ? 'success' : 'danger'">
            {{ scope.row.callStatus === '0' ? '成功' : '失败' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="响应时间(ms)" align="center" prop="responseTime" width="120">
        <template #default="scope">
          <el-tag :type="getResponseTimeType(scope.row.responseTime)">{{ scope.row.responseTime }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="调用IP" align="center" prop="callIp" width="130" />
      <el-table-column label="调用用户" align="center" prop="callUser" :show-overflow-tooltip="true" />
      <el-table-column label="调用时间" align="center" prop="callTime" width="160">
        <template #default="scope">
          <span>{{ parseTime(scope.row.callTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleView(scope.row)"
            v-hasPermi="['api:log:query']"
          >查看</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['api:log:remove']"
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

    <!-- API调用日志详情对话框 -->
    <el-dialog title="API调用日志详情" v-model="viewOpen" width="800px" append-to-body>
      <el-descriptions :column="2" border>
        <el-descriptions-item label="API名称">{{ viewForm.apiName }}</el-descriptions-item>
        <el-descriptions-item label="API路径">{{ viewForm.apiPath }}</el-descriptions-item>
        <el-descriptions-item label="请求方法">
          <el-tag :type="getMethodTagType(viewForm.httpMethod)">{{ viewForm.httpMethod }}</el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="调用状态">
          <el-tag :type="viewForm.callStatus === '0' ? 'success' : 'danger'">
            {{ viewForm.callStatus === '0' ? '成功' : '失败' }}
          </el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="响应时间(ms)">{{ viewForm.responseTime }}</el-descriptions-item>
        <el-descriptions-item label="调用IP">{{ viewForm.callIp }}</el-descriptions-item>
        <el-descriptions-item label="调用用户">{{ viewForm.callUser }}</el-descriptions-item>
        <el-descriptions-item label="调用时间">{{ parseTime(viewForm.callTime) }}</el-descriptions-item>
        <el-descriptions-item label="请求参数" :span="2">
          <pre>{{ viewForm.requestParams }}</pre>
        </el-descriptions-item>
        <el-descriptions-item label="响应结果" :span="2">
          <pre>{{ viewForm.responseResult }}</pre>
        </el-descriptions-item>
        <el-descriptions-item label="错误信息" :span="2" v-if="viewForm.errorMessage">
          <pre style="color: red">{{ viewForm.errorMessage }}</pre>
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
import { listLog, getLog, delLog, cleanLog } from "@/api/api/log";

export default {
  name: "Log",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // API调用日志表格数据
      logList: [],
      // 弹出层标题
      title: "",
      // 是否显示查看弹出层
      viewOpen: false,
      // 日期范围
      dateRange: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        apiName: undefined,
        httpMethod: undefined,
        callStatus: undefined,
        beginTime: undefined,
        endTime: undefined
      },
      // 查看表单参数
      viewForm: {}
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询API调用日志列表 */
    getList() {
      this.loading = true;
      if (this.dateRange && this.dateRange.length === 2) {
        this.queryParams.beginTime = this.dateRange[0];
        this.queryParams.endTime = this.dateRange[1];
      } else {
        this.queryParams.beginTime = undefined;
        this.queryParams.endTime = undefined;
      }
      listLog(this.queryParams).then(response => {
        this.logList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.dateRange = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.logId)
      this.multiple = !selection.length
    },
    /** 查看按钮操作 */
    handleView(row) {
      const logId = row.logId;
      getLog(logId).then(response => {
        this.viewForm = response.data;
        this.viewOpen = true;
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const logIds = row.logId || this.ids;
      this.$modal.confirm('是否确认删除API调用日志编号为"' + logIds + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(() => {
          return delLog(logIds);
        }).then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        }).catch(() => {});
    },
    /** 清空按钮操作 */
    handleClean() {
      this.$modal.confirm('是否确认清空所有API调用日志数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(() => {
          return cleanLog();
        }).then(() => {
          this.getList();
          this.$modal.msgSuccess("清空成功");
        }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('api/manage/log/export', {
        ...this.queryParams
      }, `log_${new Date().getTime()}.xlsx`)
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
    },
    /** 获取响应时间标签类型 */
    getResponseTimeType(time) {
      if (time < 200) {
        return 'success';
      } else if (time < 500) {
        return '';
      } else if (time < 1000) {
        return 'warning';
      } else {
        return 'danger';
      }
    }
  }
};
</script>