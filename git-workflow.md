# Git工作流程指南

## 仓库信息

- **仓库地址**: https://github.com/R-mark-jerry/api.git
- **默认分支**: master
- **克隆命令**: `git clone https://github.com/R-mark-jerry/api.git`

## 基本Git操作

### 1. 克隆仓库
```bash
git clone https://github.com/R-mark-jerry/api.git
cd api
```

### 2. 查看状态
```bash
git status
```

### 3. 添加文件到暂存区
```bash
# 添加所有文件
git add .

# 添加特定文件
git add 文件名
```

### 4. 提交更改
```bash
git commit -m "提交说明"
```

### 5. 推送到远程仓库
```bash
git push origin master
```

### 6. 拉取远程更新
```bash
git pull origin master
```

## 分支管理

### 1. 创建新分支
```bash
git checkout -b 分支名
```

### 2. 切换分支
```bash
git checkout 分支名
```

### 3. 查看所有分支
```bash
git branch -a
```

### 4. 合并分支
```bash
git checkout master
git merge 分支名
```

### 5. 删除分支
```bash
# 删除本地分支
git branch -d 分支名

# 删除远程分支
git push origin --delete 分支名
```

## 提交规范

### 提交信息格式
```
<类型>(<范围>): <描述>

[可选的正文]

[可选的脚注]
```

### 类型说明
- `feat`: 新功能
- `fix`: 修复bug
- `docs`: 文档更新
- `style`: 代码格式调整
- `refactor`: 代码重构
- `test`: 测试相关
- `chore`: 构建过程或辅助工具的变动

### 示例
```bash
git commit -m "feat(用户管理): 添加用户导出功能"
git commit -m "fix(登录): 修复密码加密问题"
git commit -m "docs: 更新README文档"
```

## 项目特定工作流程

### 1. 功能开发流程
```bash
# 1. 创建功能分支
git checkout -b feature/用户管理

# 2. 开发功能
# ... 编写代码 ...

# 3. 提交代码
git add .
git commit -m "feat(用户管理): 实现用户CRUD功能"

# 4. 推送分支
git push origin feature/用户管理

# 5. 合并到主分支
git checkout master
git merge feature/用户管理
git push origin master

# 6. 删除功能分支
git branch -d feature/用户管理
git push origin --delete feature/用户管理
```

### 2. Bug修复流程
```bash
# 1. 创建修复分支
git checkout -b hotfix/登录问题

# 2. 修复问题
# ... 修复代码 ...

# 3. 提交修复
git add .
git commit -m "fix(登录): 修复验证码不显示问题"

# 4. 推送并合并
git push origin hotfix/登录问题
git checkout master
git merge hotfix/登录问题
git push origin master
```

## 常见问题解决

### 1. 推送失败
```bash
# 先拉取远程更新
git pull origin master

# 解决冲突后重新提交
git add .
git commit -m "解决合并冲突"
git push origin master
```

### 2. 撤销提交
```bash
# 撤销最后一次提交，保留更改
git reset --soft HEAD~1

# 撤销最后一次提交，丢弃更改
git reset --hard HEAD~1
```

### 3. 查看提交历史
```bash
# 查看简洁历史
git log --oneline

# 查看详细历史
git log --graph --pretty=format:'%h - %an, %ar : %s'
```

### 4. 暂存当前工作
```bash
# 暂存当前更改
git stash

# 查看暂存列表
git stash list

# 恢复暂存
git stash pop
```

## 项目结构说明

```
api/
├── backend/                 # 后端代码
│   ├── api-admin/          # 启动模块
│   ├── api-common/         # 通用模块
│   ├── api-framework/      # 框架模块
│   ├── api-system/         # 系统模块
│   └── api-tool/           # 工具模块
├── frontend/               # 前端代码
├── docs/                   # 文档
│   └── sql/               # 数据库脚本
├── README.md              # 项目说明
├── QUICK_START.md         # 快速启动指南
└── .gitignore            # Git忽略文件
```

## 版本发布流程

### 1. 创建发布分支
```bash
git checkout -b release/v1.0.0
```

### 2. 更新版本信息
- 更新README.md中的版本号
- 更新package.json中的版本号
- 更新CHANGELOG.md

### 3. 提交发布
```bash
git add .
git commit -m "release: 发布v1.0.0版本"
git push origin release/v1.0.0
```

### 4. 合并到主分支并打标签
```bash
git checkout master
git merge release/v1.0.0
git tag v1.0.0
git push origin master --tags
```

## 协作开发

### 1. Fork仓库
1. 在GitHub上Fork原仓库
2. 克隆Fork后的仓库
3. 添加上游仓库：`git remote add upstream https://github.com/R-mark-jerry/api.git`

### 2. 同步上游更新
```bash
git fetch upstream
git checkout master
git merge upstream/master
git push origin master
```

### 3. 提交Pull Request
1. 创建功能分支
2. 开发并提交代码
3. 推送到Fork仓库
4. 在GitHub上创建Pull Request

---

**注意**: 请确保在提交代码前先拉取远程最新更新，避免冲突。