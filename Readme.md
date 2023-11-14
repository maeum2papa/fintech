## AWS EC2 + Docker
http://43.201.224.74:8080

## 상담저장
### 요청
**POST**
/loans/counsel
~~~json
{
    "name" : "홍길동",
    "phone":"01040768612",
    "email":"hong@kakao.com",
    "memo":"주담대문의"
}
~~~
### 응답
~~~json
{
    "statusCode": "200",
    "message": "OK",
    "timeStamp": "2023-11-11T02:34:23.426113",
    "data": {
        "counselId": 1,
        "name": "홍길동",
        "createDate": "2023-11-11T02:34:23.381515"
    }
}
~~~

</br>

## 상담조회
### 요청
**POST**
/loans/counsel/1
### 응답
~~~json
{
    "statusCode": "200",
    "message": "OK",
    "timeStamp": "2023-11-09T14:25:13.167004",
    "data": {
        "counselId": 1,
        "name": "홍길동",
        "phone": "01040768612",
        "email": "hong@kakao.com",
        "memo": "주담대문의",
        "createDate": "2023-11-09T14:24:26"
    }
}
~~~

</br>

## 상담완료처리
### 요청
**PUT**
/loans/counsel/1/update
~~~json
{
    "adminMemo" : "상담 완료"
}
~~~
### 응답
~~~json
{
    "statusCode": "200",
    "message": "OK",
    "timeStamp": "2023-11-11T02:38:23.392563",
    "data": {
        "name": "홍길동",
        "phone": "01040768612",
        "email": "hong@kakao.com",
        "memo": "주담대문의",
        "adminMemo": "상담 완료",
        "counselDate": "2023-11-11T02:38:23.379598",
        "createDate": "2023-11-11T02:34:23"
    }
}
~~~

</br>

## 상담취소
### 요청
**PUT**
/loans/counsel/1/cancel
### 응답
~~~json
{
    "statusCode": "200",
    "message": "OK",
    "timeStamp": "2023-11-09T14:25:28.232259",
    "data": true
}
~~~

</br>

## 대출신청
### 요청
**POST**
/check
~~~json
{
    "loanRepaymentPeriod": 360,
    "email": "hong@gmail.com",
    "address": "서울 마포구 성산동",
    "addressDetail": "성산시영아파트 7동",
    "income": 50000000,
    "ltv": 0.7,
    "dsr": 0.6,
    "loanKind": "HOUSE",
    "phone": "01040768612",
    "otherYearPrincipalAndInterrest": 0,
    "interestRateKind": "FIXED",
    "asset": 630000000,
    "name": "홍길동",
    "amount": 430000000
}
~~~
### 응답
~~~json
{
    "statusCode": "200",
    "message": "OK",
    "timeStamp": "2023-11-11T02:40:29.868499",
    "data": {
        "id": 1,
        "createDate": "2023-11-11T02:40:29.845877"
    }
}
~~~

</br>

## 대출심사
### 요청
**PUT**
/check/1
### 응답
~~~json
{
    "statusCode": "200",
    "message": "OK",
    "timeStamp": "2023-11-11T02:41:21.819421",
    "data": {
        "id": 1,
        "examinationDate": "2023-11-11T02:41:21.806861",
        "status": "APPROVED"
    }
}
~~~

</br>

## 대출심사결과조회
### 요청
**POST**
/check/1
### 응답
~~~json
{
    "statusCode": "200",
    "message": "OK",
    "timeStamp": "2023-11-11T02:43:38.091914",
    "data": {
        "id": 1,
        "name": "홍길동",
        "phone": "01040768612",
        "email": "hong@gmail.com",
        "address": "서울 마포구 성산동",
        "addressDetail": "성산시영아파트 7동",
        "loanKind": "HOUSE",
        "otherYearPrincipalAndInterrest": 0,
        "income": 50000000,
        "asset": 630000000,
        "amount": 430000000,
        "loanRepaymentPeriod": 360,
        "interestRateKind": "FIXED",
        "interestRate": 4.0,
        "monthlyRepaymentOfPrincipalAndInterest": 1194445,
        "monthlyRepaymentAmount": 1194445,
        "monthlyRepaymentInterest": 47778,
        "totalLoanInterest": 17200080,
        "examinationDate": "2023-11-11T02:41:22",
        "status": "APPROVED",
        "createDate": "2023-11-11T02:40:30",
        "ltv": 0.7,
        "dsr": 0.6
    }
}
~~~

</br>

## 대출계약체결
### 요청
**PUT**
/check/1/contract
### 응답
~~~json
{
    "statusCode": "200",
    "message": "OK",
    "timeStamp": "2023-11-11T02:24:55.2882",
    "data": {
        "id": 1,
        "contractDate": "2023-11-11T02:24:55.277043"
    }
}
~~~