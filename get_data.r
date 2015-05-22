library(randomForest)
library(MASS)
dat103<-read.table('subject103.dat',sep=' ',header=F)
dat<-cbind(dat103[,1:3],dat103[,5:13],dat103[,22:30],dat103[,39:47])
dat2<-dat[which(dat[,2]!=0),]  #remove activity 0
x<-dat2[,3:30]  #separate sensor data from time stamp and activity
y<-dat2[,2]     #column 2 is activity
#remove data with Heart Rate=NA
x[,1]<-replace(x[,1],is.na(x[,1]),0) 
xd<-x[which(x[,1]>0),]
yd<-y[which(x[,1]>0)]
I<-c(1:length(yd))
sI<-sample(I,5000)  #sample dataset for training data
xe<-xd[sI,]
ye<-yd[sI]
ye<-as.factor(ye)
#replace NA with mean
for (i in 1:ncol(xe)){ 
  m<-mean(xe[,i],na.rm=T)
  xe[,i]<-replace(xe[,i], is.na(xe[,i]), m)
    }


fit<-randomForest(xe,ye)
sum(ye==predict(fit,xe))/length(ye)
#sample again to get test data
tI<-sample(I,500)
testx<-xd[tI,]
testy<-yd[tI]
sum(testy==predict(fit,testx))/length(testy)
