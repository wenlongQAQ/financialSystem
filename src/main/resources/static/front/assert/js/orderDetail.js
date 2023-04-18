const deleteOrderType = (ids) => {
    return axios({
        url: '/orderType',
        method: 'delete',
        params: { ids }
    })
}