function calAge(strBirthday){
    let returnAge;
    // 根据生日计算年龄
    //以下五行是为了获取出生年月日，如果是从身份证上获取需要稍微改变一下
    let birthYear = strBirthday.getFullYear();
    console.log(birthYear)

    let birthMonth = strBirthday.getMonth() + 1;
    let birthDay = strBirthday.getDate();
    let d = new Date();
    let nowYear = d.getFullYear();
    let nowMonth = d.getMonth() + 1;
    let nowDay = d.getDate();
    if(nowYear == birthYear){
        returnAge = 0;//同年 则为0岁
    } else{
        var ageDiff = nowYear - birthYear ; //年之差
        if(ageDiff > 0){
            if(nowMonth == birthMonth) {
                var dayDiff = nowDay - birthDay;//日之差
                if (dayDiff < 0) {
                    returnAge = ageDiff - 1;
                } else {
                    returnAge = ageDiff;
                }
                console.log(returnAge)
            }else {
                var monthDiff = nowMonth - birthMonth;//月之差
                if(monthDiff < 0) {
                    returnAge = ageDiff - 1;
                }else {
                    returnAge = ageDiff ;
                }
            }
        } else {
            returnAge = -1;//返回-1 表示出生日期输入错误 晚于今天
        }
    }
    return returnAge;
}