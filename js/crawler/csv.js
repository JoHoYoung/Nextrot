const fs = require("fs");
const uuid = require("uuid");

const fetchLine = (str) => {
  let datas = str.split(",");

  let singerName = datas[0];
  let songName = datas[1];
  let link = datas[2];

  let key;
  if(link.indexOf("/watch?v=") === -1){
    key = (((link.split("https://youtu.be/"))[1]).split('\r'))[0];
  }else{
    key = (((((link.split("/watch?v="))[1]).split("&"))[0]).split('\r'))[0];
  }
//  console.log("KEY", key)
  key = (key.split('"\r'))[0];
  console.log(singerName)
  //singerName = (singerName.split('"'))[1];
  return {
    _id : uuid.v4(),
    singerName,
    name:songName,
    singerId:"Non Singer At Promotion Data",
    lyrics: "원곡 찾기 노가다 뛰면 찾을예정",
    like:0,
    view:0,
    video:[{
      _id : uuid.v4(),
      songId:"Empty Set",
      key,
      like:0,
      view:0,
      createdAt : {"$date" : (new Date()).toISOString()},
      updatedAt : {"$date" : (new Date()).toISOString()}
    }],
    //{ "$date": "2014-01-22T14:56:59.301Z" }
    createdAt : {"$date" : (new Date()).toISOString()},
    updatedAt : {"$date" : (new Date()).toISOString()}
  }
};

let mistrot = {data : []};
let mistertrot = {data : []};
async function mistrotF(){
  let data = (await fs.readFileSync("/Users/HY/Desktop/mistrot5.csv")).toString().split("\n")

  for(let i=1;i<data.length;i++){
    console.log(fetchLine(data[i]));
    mistrot.data.push(fetchLine(data[i]));
  }

  // console.log(mistrot)
  fs.writeFileSync(`/Users/HY/WebstormProjects/Dailyissue/promotion/mistrot.json`,JSON.stringify(mistrot));
  let data2 = (await fs.readFileSync("/Users/HY/Desktop/mistertrot3.csv")).toString().split("\n")
  for(let i=1;i<data2.length;i++){
    mistertrot.data.push(fetchLine(data2[i]));
  }
  fs.writeFileSync(`/Users/HY/WebstormProjects/Dailyissue/promotion/mistertrot.json`,JSON.stringify(mistertrot));

}

mistrotF();