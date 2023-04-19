const getAdvice = (userId,type) => {
    return axios({
        url: '/advice',
        method: 'get',
        params: { userId,type }
    })
}
const getAdvicePage=(userId,page,pageSize)=>{
    return axios({
        url:'/advice/page',
        method:'get',
        params:{userId,page,pageSize}

    })
}