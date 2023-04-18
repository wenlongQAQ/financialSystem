const deleteOrder = (ids) => {
    return axios({
        url: '/order',
        method: 'delete',
        params: { ids }
    })
}
