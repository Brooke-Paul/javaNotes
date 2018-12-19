---
title: github更改作者的提交信息
date: 2018-12-20 07:01:34 
comments: true  
categories:  
- git filter-branch --env-filter
- collection
tags:
- git  
---

## 问题发现
`github`拉取代码分支后提交后贡献值不增加


## 解决办法
执行第一步：
`git config --global user.email "youremail@googl.com" `  
`git config --global user.name "your name"`

执行第二步：
`git clone --bare https://github.com/user/repo.git`
`cd repo.git`

执行第三步：

```git filter-branch --env-filter '

OLD_EMAIL="your-old-email@example.com" // 你的旧的email账号
CORRECT_NAME="Your Correct Name"   // 你的新的用户名
CORRECT_EMAIL="your-correct-email@example.com" // 你的新的email账号

if [ "$GIT_COMMITTER_EMAIL" = "$OLD_EMAIL" ]
then
    export GIT_COMMITTER_NAME="$CORRECT_NAME"
    export GIT_COMMITTER_EMAIL="$CORRECT_EMAIL"
fi
if [ "$GIT_AUTHOR_EMAIL" = "$OLD_EMAIL" ]
then
    export GIT_AUTHOR_NAME="$CORRECT_NAME"
    export GIT_AUTHOR_EMAIL="$CORRECT_EMAIL"
fi
' --tag-name-filter cat -- --branches --tags
```

执行第四步：
`git push --force --tags origin 'refs/heads/*'`

ok，至此作者信息修改完毕


此时本地仓库提交代码会失败，首先执行 更新：`git pull origin master --allow-unrelated-histories`