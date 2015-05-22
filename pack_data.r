data<-read.table('subject103.dat',sep=' ',header=F)
#remove all rows with label=0 from dataset
I=which(data[,2]==0)
dat103 =data[-I,]
#set the first value of HR to fill NA's
dat103[1,3]=91
n=nrow(dat103)
#fill NA's in HR
#assume the first data point is never NA
hr<-dat103[,3]
filler<-hr[1]
for (i in 2:n){
  if (is.na(hr[i])) {hr[i]<-filler}
  else {filler<-hr[i]}
}
dat103[,3]<-hr
#set all other NA's to the means
no_na=c(rep(1,54))
for (i in 4:54){ 
  m<-mean(dat103[,i],na.rm=T)
  dat103[,i]<-replace(dat103[,i], is.na(dat103[,i]), m)}
#check if there are any na's
for (i in 1:54){
  no_na[i]<-sum(is.na(dat103[,i]))
}
#create the subject ID column
col1=rep(103,n)
now=Sys.time()
options(digits.secs=2)
col2=rep(now,n)
mfactor<-seq(1:n)
mfactor<-mfactor*.01
col2=col2+mfactor
col3=dat103[,2]
col4<-as.character()
cob='['
ccb=']'
y<-as.matrix(dat103[,3:54])
for(i in 1:n){
x<-paste(y[i,],collapse=",")
col4[i]<-paste(cob,x,ccb)}
testhold=cbind(col1,col2,col3,col4)
#test code with 20000 examples, then copy more from 20001 to end
test<-testhold[20001:n,]
write.table(test,file='sub103r4.csv',row.names=F,col.names=F,sep=',')

