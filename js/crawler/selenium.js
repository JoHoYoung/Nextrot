const webdriver = require('selenium-webdriver')
const Key = webdriver.Key;
const By = webdriver.By;

const fs = require('fs');

const uuid = require('uuid')

function sleep(timeinterval) {
  return new Promise((resolve, reject) => {
    setTimeout((function () {
      resolve(true)
    }), timeinterval)
  })
}


async function getDriver() {
  return new Promise((resolve, reject) => {
    new webdriver.Builder().forBrowser('firefox').build().then(async (driver) => {
      resolve(driver)
    })
  })
}

async function getSingerList() {
  let driver = await getDriver();
  let result = [];

  return new Promise(async (resolve, reject) => {
    await driver.get("https://www.google.com/search?q=%ED%8A%B8%EB%A1%9C%ED%8A%B8%EA%B0%80%EC%88%98&oq=%ED%8A%B8%EB%A1%9C%ED%8A%B8%EA%B0%80%EC%88%98&aqs=chrome..69i57j69i61l3.1816j0j8&sourceid=chrome&ie=UTF-8")
    await driver.findElements(By.css("#extabar > div:nth-child(2) > div > div > g-scrolling-carousel > div:nth-child(1) > div > a"))
      .then((s) => {
        let tasks = [];
        for (let i = 0; i < s.length; i++) {
          tasks.push(s[i].getAttribute("aria-label").then((k) => {
            result.push(k);
          }));
        }
        Promise.all(tasks).then(() => {
          driver.close();
          resolve(result);

        })
      }).catch(e => {
        driver.close();
        console.log(e);
      })
  });
}

async function getSongList(singer) {
  let driver = await getDriver();
  let searchKey = `${singer} 노래`;
  let result = [];
  return new Promise(async (resolve, reject) => {
    await driver.get("https://www.google.com/");
    await sleep(1000);
    await driver.findElement(By.css("#tsf > div:nth-child(2) > div.A8SBwf > div.RNNXgb > div > div.a4bIc > input")).sendKeys(searchKey).catch(() => {
      getSongList(singer);
    });
    await driver.findElement(By.css("#tsf > div:nth-child(2) > div.A8SBwf > div.RNNXgb > div > div.a4bIc > input")).sendKeys(Key.RETURN).catch(() => {
      getSongList(singer);
    });
    await sleep(1000).catch(() => {
      resolve([])
    });
    await driver.findElements(By.css("#extabar > div > div > div > div > div > div:nth-child(2) > div > g-scrolling-carousel > div:nth-child(1) > div > div"))
      .then(async (s) => {
        console.log(s.length);
        let cnt = 0;
        let task = [];
        for (let i = 0; i < s.length; i++) {
          for (let j = 0; j < 4; j++) {
            cnt++;
            task.push(driver.findElement(By.css(`#extabar > div > div > div > div > div > div:nth-child(2) > div > g-scrolling-carousel > div:nth-child(1) > div > div:nth-child(${i + 1}) > div > div:nth-child(${j + 1}) > div > a > div:nth-child(2) > div > div > div:nth-child(1)`))
              .then((el) => {
                el.getText().then((data) => {
                  result.push(data);
                });
              }));
            await sleep(300);
            if (cnt % 12 === 0) {
              await driver.findElement(By.css("#extabar > div > div > div >div >div >div > div >g-scrolling-carousel > div:nth-child(3) > g-right-button")).click();
            }
          }
        }
        Promise.all(task).then(() => {
          driver.close();
          resolve(result);
        })
      }).catch((e) => {
        driver.close();
        resolve([]);
        console.log(e);
      })
  });
  //driver.find
}

async function getVideo(singer, song, depth) {
  return new Promise(async (resolve, reject) => {

    if (depth >= 2) {
      console.log("DEPTH ERROR");
      resolve("");
    } else {

      let driver = await getDriver();
      let searchKey = `${singer} ${song}`;
      await driver.get("https://www.google.com/");
      await sleep(1000).catch(() => {
        resolve("")
      });
      await driver.findElement(By.css("#tsf > div:nth-child(2) > div.A8SBwf > div.RNNXgb > div > div.a4bIc > input")).sendKeys(searchKey).catch(() => {
        getVideo(singer, song, depth + 1);
      });
      await driver.findElement(By.css("#tsf > div:nth-child(2) > div.A8SBwf > div.RNNXgb > div > div.a4bIc > input")).sendKeys(Key.RETURN).catch(() => {
        getVideo(singer, song, depth + 1);
      });
      await sleep(2500);
      await driver.findElement(By.css("#search > div > div > div > div > div > div > div > div > div > div:nth-child(2) > div > div > div> div > a")).then((el) => {
        el.getAttribute("href").then((k) => {
          driver.close();
          resolve(k);
        });
      }).catch((e) => {
        resolve(getVideo(singer, song, depth + 1));
      })
    }

  })
}

async function getLyrics(singer, name) {
  return new Promise(async (resolve, reject) => {
    let driver = await getDriver();
    let searchKey = `https://music.bugs.co.kr/search/integrated?q=${singer} ${name}`;
    await driver.get(searchKey).catch(() => {
      resolve("")
    });
    await driver.findElement(By.css("#container > section.sectionPadding.lyrics > div > table > tbody > tr:nth-child(2) > td > a")).then(async (el) => {
      let link = await el.getAttribute("href");
      await driver.get(link);
      driver.findElement(By.css("#container > section.sectionPadding.contents.lyrics > div > div > xmp")).then(async (data) => {
        let lyrics = await data.getText();
        driver.close();
        resolve(lyrics);
      }).catch(() => {
        resolve("")
      });
    }).catch((e) => {
      driver.close();
      resolve("");
    })
  });
}

const SINGER = (name) => {
  return {
    id: uuid.v4(),
    name,
    like: 0,
    songs: [],
    createdAt: Date.now(),
    updatedAt: Date.now(),
  }
};
const SONG = (name, lyrics) => {
  return {
    id: uuid.v4()
    , name, lyrics,
    like: 0,
    view: 0,
    video: [],
    createdAt: Date.now(),
    updatedAt: Date.now()
  }
};
const VIDEO = (key) => {
  return {
    id: uuid.v4(),
    like: 0,
    view: 0,
    key,
    createdAt: Date.now(),
    updatedAt: Date.now()
  }
};

async function solution() {
  // let singers = await getSignerList;
  // console.log(singers);
  // let a = await getSongList("송가인");
  // console.log(a);
  // let id = uuid.v4;
//  let singers = await getSingerList();
  let singers = ['송가인'];
  for (let i = 0; i < singers.length; i++) {
    let Singer = SINGER(singers[i]);
    let songs = await getSongList(singers[i]);
    console.log(songs);
    for (let j = 0; j < songs.length; j++) {
      await sleep(600);
      let lyrics = await getLyrics(singers[i], songs[j]);
      await sleep(300);
      let videoKey = await getVideo(singers[i], songs[j], 0);
      let Song = SONG(songs[j], lyrics);
      Song.video.push(VIDEO(videoKey));
      Singer.songs.push(Song);
      console.log(Singer);
    }
    fs.writeFileSync(`/Users/HY/WebstormProjects/Dailyissue/data/${singers[i]}.json`, JSON.stringify(Singer));
  }
}

solution();