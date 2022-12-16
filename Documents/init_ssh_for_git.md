# 困ったらline, teams, Discordなどで連絡を
## ssh(通信方法)の設定
### ssh用のディレクトリ(~/.ssh)の存在を確認
```
ls ~/.ssh
```
ここでid_rsaというファイルが存在した場合連絡してください

### No such file or directoryと出た場合のみ実行
```
mkdir ~/.ssh
```

### ~/.sshに移動
```
cd ~/.ssh
```

### sshキーの生成
```
ssh-keygen -t rsa
```
何か聞かれたらエンターを押せばOK
**作られたid_rsa.pubが鍵(公開鍵)です**

### 鍵をGitHubに登録する
1. https://github.com/settings/ssh
ここにアクセスしてください
2. 右上の「New SSH key」(緑のボタン)をクリック
3. Titleは任意(適当に決めてOK)
4. Keyに鍵をペーストします
5. 「Add SSH key」(緑のボタン)をクリックして
6. SSH keysのAuthentication Keysに何か追加されていればOK(ブラウザを閉じてOK)

#### 鍵のコピー方法(ubuntu)
##### ツールをインストールしてコピーする方法(推奨)
```
sudo apt install xsel
cat ~/.ssh/id_rsa.pub | xsel -bi
```
パスワードを聞かれたら入力してください

##### id_rsa.pubをatomなどで開いてコピーする方法
```
atom ~/.ssh/id_rsa.pub
```

##### id_rsa.pubを端末上に出力してコピーする方法
```
cat ~/.ssh/id_rsa.pub
```
上記コマンドを実行して出力された文字列をコピーしてください

### 鍵が使用できるか確かめる
```
ssh -T git@github.com
```
何か聞かれたらyesと入力してください

### sshをリポジトリで使用できるようにします
1. atomで~/.gitconfigを開きます
```
atom ~/.gitconfig
```
2. 以下を書き込みます
```
[url "git@github.com:"]
    InsteadOf = https://github.com/
    InsteadOf = git@github.com:
```


Writer: KK-Konbannha
