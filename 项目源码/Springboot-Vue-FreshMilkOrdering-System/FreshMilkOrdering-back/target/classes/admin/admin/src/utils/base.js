const base = {
    get() {
        return {
            url : "http://localhost:8080/xiannaidinggou/",
            name: "xiannaidinggou",
            // 退出到首页链接
            indexUrl: 'http://localhost:8080/xiannaidinggou/front/index.html'
        };
    },
    getProjectName(){
        return {
            projectName: "鲜牛奶订购系统"
        } 
    }
}
export default base
