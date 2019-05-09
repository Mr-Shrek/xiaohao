package com.yhw.alixiaohao.bean;

import java.util.List;

public class GetUBean {

    /**
     * result : {"createtime":"","uprice":"9","checkCellnumber":"18600691705","balanceNum":4,"surplusMinute":"0","bindTels":[{"specid":3,"uid":2,"usedTime":"3","bindStartTime":"2018112311","bindCellnumber":"17137202423","id":1,"specTC":{"sname":"月卡","sdesc":"1个月","stime":"600","id":3,"sprice":3.99,"fprice":0.2,"status":"on"},"smallNumber":{"area":"北京","number":"17137202423","id":6},"bindEndTime":"2019120111","bindStatus":"on"},{"specid":3,"uid":2,"usedTime":"0","bindStartTime":"2018112311","bindCellnumber":"17191288354","id":2,"specTC":{"sname":"月卡","sdesc":"1个月","stime":"600","id":3,"sprice":3.99,"fprice":0.2,"status":"on"},"smallNumber":{"area":"北京","number":"17191288354","id":7},"bindEndTime":"2019120111","bindStatus":"off"},{"specid":3,"uid":2,"usedTime":"0","bindStartTime":"2019011312","bindCellnumber":"17191736970","id":12,"specTC":{"sname":"月卡","sdesc":"1个月","stime":"600","id":3,"sprice":3.99,"fprice":0.2,"status":"on"},"smallNumber":{"area":"上海","number":"17191736970","id":16},"bindEndTime":"2019012012","bindStatus":"off"},{"specid":2,"uid":2,"usedTime":"0","bindStartTime":"2019011312","bindCellnumber":"17191736970","id":13,"specTC":{"sname":"周卡","sdesc":"7天","stime":"150","id":2,"sprice":2,"fprice":0.2,"status":"on"},"smallNumber":{"area":"上海","number":"17191736970","id":16},"bindEndTime":"2019012012","bindStatus":"off"}],"id":2,"bindStatus":"on","status":"on"}
     * code : S00000
     * api : getu
     */

    private ResultBean result;
    private String code;
    private String api;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public static class ResultBean {

        @Override
        public String toString() {
            return super.toString();
        }

        /**
         * createtime :
         * uprice : 9
         * checkCellnumber : 18600691705
         * balanceNum : 4
         * surplusMinute : 0
         * bindTels : [{"specid":3,"uid":2,"usedTime":"3","bindStartTime":"2018112311","bindCellnumber":"17137202423","id":1,"specTC":{"sname":"月卡","sdesc":"1个月","stime":"600","id":3,"sprice":3.99,"fprice":0.2,"status":"on"},"smallNumber":{"area":"北京","number":"17137202423","id":6},"bindEndTime":"2019120111","bindStatus":"on"},{"specid":3,"uid":2,"usedTime":"0","bindStartTime":"2018112311","bindCellnumber":"17191288354","id":2,"specTC":{"sname":"月卡","sdesc":"1个月","stime":"600","id":3,"sprice":3.99,"fprice":0.2,"status":"on"},"smallNumber":{"area":"北京","number":"17191288354","id":7},"bindEndTime":"2019120111","bindStatus":"off"},{"specid":3,"uid":2,"usedTime":"0","bindStartTime":"2019011312","bindCellnumber":"17191736970","id":12,"specTC":{"sname":"月卡","sdesc":"1个月","stime":"600","id":3,"sprice":3.99,"fprice":0.2,"status":"on"},"smallNumber":{"area":"上海","number":"17191736970","id":16},"bindEndTime":"2019012012","bindStatus":"off"},{"specid":2,"uid":2,"usedTime":"0","bindStartTime":"2019011312","bindCellnumber":"17191736970","id":13,"specTC":{"sname":"周卡","sdesc":"7天","stime":"150","id":2,"sprice":2,"fprice":0.2,"status":"on"},"smallNumber":{"area":"上海","number":"17191736970","id":16},"bindEndTime":"2019012012","bindStatus":"off"}]
         * id : 2
         * bindStatus : on
         * status : on
         */

        private String createtime;
        private String uprice;
        private String checkCellnumber;
        private int balanceNum;
        private String surplusMinute;
        private int id;
        private String bindStatus;
        private String status;
        private List<BindTelsBean> bindTels;

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getUprice() {
            return uprice;
        }

        public void setUprice(String uprice) {
            this.uprice = uprice;
        }

        public String getCheckCellnumber() {
            return checkCellnumber;
        }

        public void setCheckCellnumber(String checkCellnumber) {
            this.checkCellnumber = checkCellnumber;
        }

        public int getBalanceNum() {
            return balanceNum;
        }

        public void setBalanceNum(int balanceNum) {
            this.balanceNum = balanceNum;
        }

        public String getSurplusMinute() {
            return surplusMinute;
        }

        public void setSurplusMinute(String surplusMinute) {
            this.surplusMinute = surplusMinute;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getBindStatus() {
            return bindStatus;
        }

        public void setBindStatus(String bindStatus) {
            this.bindStatus = bindStatus;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public List<BindTelsBean> getBindTels() {
            return bindTels;
        }

        public void setBindTels(List<BindTelsBean> bindTels) {
            this.bindTels = bindTels;
        }

        public static class BindTelsBean {
            /**
             * specid : 3
             * uid : 2
             * usedTime : 3
             * bindStartTime : 2018112311
             * bindCellnumber : 17137202423
             * id : 1
             * specTC : {"sname":"月卡","sdesc":"1个月","stime":"600","id":3,"sprice":3.99,"fprice":0.2,"status":"on"}
             * smallNumber : {"area":"北京","number":"17137202423","id":6}
             * bindEndTime : 2019120111
             * bindStatus : on
             */

            private int specid;
            private int uid;
            private String usedTime;
            private String bindStartTime;
            private String bindCellnumber;
            private int id;
            private SpecTCBean specTC;
            private SmallNumberBean smallNumber;
            private String bindEndTime;
            private String bindStatus;

            public int getSpecid() {
                return specid;
            }

            public void setSpecid(int specid) {
                this.specid = specid;
            }

            public int getUid() {
                return uid;
            }

            public void setUid(int uid) {
                this.uid = uid;
            }

            public String getUsedTime() {
                return usedTime;
            }

            public void setUsedTime(String usedTime) {
                this.usedTime = usedTime;
            }

            public String getBindStartTime() {
                return bindStartTime;
            }

            public void setBindStartTime(String bindStartTime) {
                this.bindStartTime = bindStartTime;
            }

            public String getBindCellnumber() {
                return bindCellnumber;
            }

            public void setBindCellnumber(String bindCellnumber) {
                this.bindCellnumber = bindCellnumber;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public SpecTCBean getSpecTC() {
                return specTC;
            }

            public void setSpecTC(SpecTCBean specTC) {
                this.specTC = specTC;
            }

            public SmallNumberBean getSmallNumber() {
                return smallNumber;
            }

            public void setSmallNumber(SmallNumberBean smallNumber) {
                this.smallNumber = smallNumber;
            }

            public String getBindEndTime() {
                return bindEndTime;
            }

            public void setBindEndTime(String bindEndTime) {
                this.bindEndTime = bindEndTime;
            }

            public String getBindStatus() {
                return bindStatus;
            }

            public void setBindStatus(String bindStatus) {
                this.bindStatus = bindStatus;
            }

            public static class SpecTCBean {
                /**
                 * sname : 月卡
                 * sdesc : 1个月
                 * stime : 600
                 * id : 3
                 * sprice : 3.99
                 * fprice : 0.2
                 * status : on
                 */

                private String sname;
                private String sdesc;
                private String stime;
                private int id;
                private double sprice;
                private double fprice;
                private String status;

                public String getSname() {
                    return sname;
                }

                public void setSname(String sname) {
                    this.sname = sname;
                }

                public String getSdesc() {
                    return sdesc;
                }

                public void setSdesc(String sdesc) {
                    this.sdesc = sdesc;
                }

                public String getStime() {
                    return stime;
                }

                public void setStime(String stime) {
                    this.stime = stime;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public double getSprice() {
                    return sprice;
                }

                public void setSprice(double sprice) {
                    this.sprice = sprice;
                }

                public double getFprice() {
                    return fprice;
                }

                public void setFprice(double fprice) {
                    this.fprice = fprice;
                }

                public String getStatus() {
                    return status;
                }

                public void setStatus(String status) {
                    this.status = status;
                }
            }

            public static class SmallNumberBean {
                /**
                 * area : 北京
                 * number : 17137202423
                 * id : 6
                 */

                private String area;
                private String number;
                private int id;

                public String getArea() {
                    return area;
                }

                public void setArea(String area) {
                    this.area = area;
                }

                public String getNumber() {
                    return number;
                }

                public void setNumber(String number) {
                    this.number = number;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }
            }
        }
    }
}
