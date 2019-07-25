var GUI =
(window["webpackJsonpGUI"] = window["webpackJsonpGUI"] || []).push([["robotplayer"],{

/***/ "./node_modules/css-loader/index.js?!./node_modules/postcss-loader/src/index.js?!./src/playground/robotplayer.css":
/*!***********************************************************************************************************************!*\
  !*** ./node_modules/css-loader??ref--5-1!./node_modules/postcss-loader/src??postcss!./src/playground/robotplayer.css ***!
  \***********************************************************************************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__(/*! ../../node_modules/css-loader/lib/css-base.js */ "./node_modules/css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, ".robotplayer_stage-only_pOsfU {\r\n    margin:0 auto;\r\n    width: 100%;\r\n    height: 100%;\r\n}\r\n\r\n.robotplayer_stage-only_pOsfU * {\r\n    -webkit-box-sizing: border-box;\r\n            box-sizing: border-box;\r\n}\r\n", ""]);

// exports
exports.locals = {
	"stage-only": "robotplayer_stage-only_pOsfU",
	"stageOnly": "robotplayer_stage-only_pOsfU"
};

/***/ }),

/***/ "./src/playground/robotplayer.css":
/*!****************************************!*\
  !*** ./src/playground/robotplayer.css ***!
  \****************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {


var content = __webpack_require__(/*! !../../node_modules/css-loader??ref--5-1!../../node_modules/postcss-loader/src??postcss!./robotplayer.css */ "./node_modules/css-loader/index.js?!./node_modules/postcss-loader/src/index.js?!./src/playground/robotplayer.css");

if(typeof content === 'string') content = [[module.i, content, '']];

var transform;
var insertInto;



var options = {"hmr":true}

options.transform = transform
options.insertInto = undefined;

var update = __webpack_require__(/*! ../../node_modules/style-loader/lib/addStyles.js */ "./node_modules/style-loader/lib/addStyles.js")(content, options);

if(content.locals) module.exports = content.locals;

if(false) {}

/***/ }),

/***/ "./src/playground/robotplayer.jsx":
/*!****************************************!*\
  !*** ./src/playground/robotplayer.jsx ***!
  \****************************************/
/*! no exports provided */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony import */ var classnames__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! classnames */ "./node_modules/classnames/index.js");
/* harmony import */ var classnames__WEBPACK_IMPORTED_MODULE_0___default = /*#__PURE__*/__webpack_require__.n(classnames__WEBPACK_IMPORTED_MODULE_0__);
/* harmony import */ var prop_types__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! prop-types */ "./node_modules/prop-types/index.js");
/* harmony import */ var prop_types__WEBPACK_IMPORTED_MODULE_1___default = /*#__PURE__*/__webpack_require__.n(prop_types__WEBPACK_IMPORTED_MODULE_1__);
/* harmony import */ var react__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! react */ "./node_modules/react/index.js");
/* harmony import */ var react__WEBPACK_IMPORTED_MODULE_2___default = /*#__PURE__*/__webpack_require__.n(react__WEBPACK_IMPORTED_MODULE_2__);
/* harmony import */ var react_dom__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! react-dom */ "./node_modules/react-dom/index.js");
/* harmony import */ var react_dom__WEBPACK_IMPORTED_MODULE_3___default = /*#__PURE__*/__webpack_require__.n(react_dom__WEBPACK_IMPORTED_MODULE_3__);
/* harmony import */ var react_redux__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! react-redux */ "./node_modules/react-redux/es/index.js");
/* harmony import */ var redux__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! redux */ "./node_modules/redux/es/index.js");
/* harmony import */ var _components_box_box_jsx__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! ../components/box/box.jsx */ "./src/components/box/box.jsx");
/* harmony import */ var _containers_gui_jsx__WEBPACK_IMPORTED_MODULE_7__ = __webpack_require__(/*! ../containers/gui.jsx */ "./src/containers/gui.jsx");
/* harmony import */ var _lib_hash_parser_hoc_jsx__WEBPACK_IMPORTED_MODULE_8__ = __webpack_require__(/*! ../lib/hash-parser-hoc.jsx */ "./src/lib/hash-parser-hoc.jsx");
/* harmony import */ var _lib_app_state_hoc_jsx__WEBPACK_IMPORTED_MODULE_9__ = __webpack_require__(/*! ../lib/app-state-hoc.jsx */ "./src/lib/app-state-hoc.jsx");
/* harmony import */ var _lib_titled_hoc_jsx__WEBPACK_IMPORTED_MODULE_10__ = __webpack_require__(/*! ../lib/titled-hoc.jsx */ "./src/lib/titled-hoc.jsx");
/* harmony import */ var scratch_vm__WEBPACK_IMPORTED_MODULE_11__ = __webpack_require__(/*! scratch-vm */ "./node_modules/scratch-vm/src/index.js");
/* harmony import */ var scratch_vm__WEBPACK_IMPORTED_MODULE_11___default = /*#__PURE__*/__webpack_require__.n(scratch_vm__WEBPACK_IMPORTED_MODULE_11__);
/* harmony import */ var _robotplayer_css__WEBPACK_IMPORTED_MODULE_12__ = __webpack_require__(/*! ./robotplayer.css */ "./src/playground/robotplayer.css");
/* harmony import */ var _robotplayer_css__WEBPACK_IMPORTED_MODULE_12___default = /*#__PURE__*/__webpack_require__.n(_robotplayer_css__WEBPACK_IMPORTED_MODULE_12__);
function _typeof(obj) { if (typeof Symbol === "function" && typeof Symbol.iterator === "symbol") { _typeof = function _typeof(obj) { return typeof obj; }; } else { _typeof = function _typeof(obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; }; } return _typeof(obj); }














if (false) {}


/*********************** body内开放接口定义 *********************** */

/**
 * 调用播放器内接口 - 提供给主应用App的接口
 */

var callJsMethod = function callJsMethod(funcName, paramStr, projectData) {
  console.log("\u4E3B\u5E94\u7528\u8BF7\u6C42\u8C03\u7528\u64AD\u653E\u5668\u63A5\u53E3: ".concat(funcName, ", \u53C2\u6570: ").concat(paramStr));

  if (funcName === 'loadNewProjectFile') {
    if (projectData instanceof ArrayBuffer) {
      console.log('文件数据为ArrayBuffer类型');

      if (projectData) {
        window.vm.callJsMethod(funcName, paramStr, projectData);
      } else {
        console.log('文件数据为空！');
      }
    } else if (typeof projectData === 'string') {
      console.log('文件数据为Base64加密字符串');

      if (projectData === 'undefined' || projectData === '' || projectData === 'null') {
        console.log('文件数据为空！');
        return;
      } // 方式一: Blob
      // let fileBlob = _base64ToBlob(base64Str);
      // console.warn(fileBlob);
      // var reader = new FileReader();
      // reader.readAsArrayBuffer(fileBlob);
      // reader.onload = (e) => {
      //     console.info(reader.result); //ArrayBuffer {}
      //     window.vm.callJsMethod('loadNewProjectFile', paramStr, reader.result);
      // }
      // 方式二: 直接ArrayBuffer


      var fileArrayBuffer = _base64ToArrayBuffer(projectData);

      console.log("\u8F6C\u6362\u540E\u7684ArrayBuffer\u7684\u957F\u5EA6: ".concat(fileArrayBuffer.length));
      window.vm.callJsMethod(funcName, paramStr, fileArrayBuffer);
    } else {
      console.log('文件数据为其他类型');

      if (!projectData) {
        console.log('文件数据为空！默认为byteArray类型');
        return;
      }

      var byteArrayBlob = new Blob([projectData], {
        type: 'application/octet-stream'
      });
      var byteArrayReader = new FileReader();
      byteArrayReader.readAsArrayBuffer(byteArrayBlob);

      reader.onload = function () {
        window.vm.callJsMethod(funcName, paramStr, byteArrayReader.result);
      };
    }
  } else {
    window.vm.callJsMethod(funcName, paramStr);
  }
};
/**
 * 向播放器发送事件 - 提供给主应用App的接口
 */


var appEventHandler = function appEventHandler(evtName, evtParamStr) {
  window.vm.appEventHandler(evtName, evtParamStr);
};
/**
 * Base64字符串 ---> ArrayBuffer
 */


function _base64ToArrayBuffer(base64String) {
  var byteString = window.atob(base64String); //base64 解码

  console.warn("base64\u89E3\u7801\u540E\u957F\u5EA6\uFF1A".concat(byteString.length));
  var arrayBuffer = new ArrayBuffer(byteString.length); //创建缓冲数组

  var intArray = new Uint8Array(arrayBuffer); //创建视图

  for (var i = 0; i < byteString.length; i++) {
    intArray[i] = byteString.charCodeAt(i);
  }

  return intArray;
}
/**
 * Base64字符串 ---> Blob
 */


function _base64ToBlob(base64String) {
  var mimeString = 'application/octet-stream'; // mime类型

  var byteString = window.atob(base64String); //base64 解码

  console.warn('base64解码后长度：', byteString.length);
  var arrayBuffer = new ArrayBuffer(byteString.length); //创建缓冲数组

  var intArray = new Uint8Array(arrayBuffer); //创建视图

  for (var i = 0; i < byteString.length; i++) {
    intArray[i] = byteString.charCodeAt(i);
  }

  return new Blob([intArray], {
    type: mimeString
  });
}
/**
 * 调用主应用App内的接口 - 提供给播放器内使用
 */


var callNativeMethod = function callNativeMethod(funcName, paramStr) {
  if (window.android) {
    console.log("\u8BF7\u6C42\u8C03\u7528\u4E3B\u5E94\u7528\u7684\u63A5\u53E3: ".concat(funcName, ", \u53C2\u6570\u662F ").concat(paramStr));
    window.android.callAndroidMethod(funcName, paramStr);
  } else {
    console.log("window.android\u4E3A\u7A7A\uFF0C\u65E0\u6CD5\u8C03\u7528!");
  }
};
/**
 * 向主应用App内发送事件 - 提供给播放器内使用
 */


var dispatchPlayerEvent = function dispatchPlayerEvent(evtName, evtParamStr) {
  if (evtName === 'logEvent') {
    // 该事件为测试页面用
    var textNode = document.createTextNode(evtParamStr + '\r\n');
    document.getElementById('logArea').appendChild(textNode);
  } else {
    if (window.android) {
      console.log("\u5411\u4E3B\u5E94\u7528\u53D1\u9001\u4E8B\u4EF6: ".concat(evtName, ", \u53C2\u6570\u662F ").concat(evtParamStr));
      window.android.playerEventHandler(evtName, evtParamStr);
    } else {
      console.log("window.android\u4E3A\u7A7A\uFF0C\u65E0\u6CD5\u8C03\u7528!");
    }
  }
};

window.callJsMethod = callJsMethod;
window.appEventHandler = appEventHandler;
window.callNativeMethod = callNativeMethod;
window.dispatchPlayerEvent = dispatchPlayerEvent;

var RobotPlayer = function RobotPlayer(_ref) {
  var projectId = _ref.projectId,
      vm = _ref.vm;
  window.vm = vm;
  return react__WEBPACK_IMPORTED_MODULE_2___default.a.createElement(_components_box_box_jsx__WEBPACK_IMPORTED_MODULE_6__["default"], {
    className: classnames__WEBPACK_IMPORTED_MODULE_0___default()(_robotplayer_css__WEBPACK_IMPORTED_MODULE_12___default.a.stageOnly)
  }, react__WEBPACK_IMPORTED_MODULE_2___default.a.createElement(_containers_gui_jsx__WEBPACK_IMPORTED_MODULE_7__["default"], {
    enableCommunity: true,
    projectId: projectId
  }));
};

RobotPlayer.propTypes = {
  projectId: prop_types__WEBPACK_IMPORTED_MODULE_1___default.a.string,
  vm: prop_types__WEBPACK_IMPORTED_MODULE_1___default.a.instanceOf(scratch_vm__WEBPACK_IMPORTED_MODULE_11___default.a).isRequired
};

var mapStateToProps = function mapStateToProps(state) {
  return {
    vm: state.scratchGui.vm
  };
};

var ConnectedPlayer = Object(react_redux__WEBPACK_IMPORTED_MODULE_4__["connect"])(mapStateToProps)(RobotPlayer); // note that redux's 'compose' function is just being used as a general utility to make
// the hierarchy of HOC constructor calls clearer here; it has nothing to do with redux's
// ability to compose reducers.

var WrappedPlayer = Object(redux__WEBPACK_IMPORTED_MODULE_5__["compose"])(_lib_app_state_hoc_jsx__WEBPACK_IMPORTED_MODULE_9__["default"], _lib_hash_parser_hoc_jsx__WEBPACK_IMPORTED_MODULE_8__["default"], _lib_titled_hoc_jsx__WEBPACK_IMPORTED_MODULE_10__["default"])(ConnectedPlayer); // 挂载根节点

var appTarget = document.createElement('div');
document.body.appendChild(appTarget);
react_dom__WEBPACK_IMPORTED_MODULE_3___default.a.render(react__WEBPACK_IMPORTED_MODULE_2___default.a.createElement(WrappedPlayer, {
  isPlayerOnly: true,
  isFullScreen: true,
  robotPlayerMode: true,
  canSave: false
}), appTarget);

/***/ })

},[["./src/playground/robotplayer.jsx","lib.min"]]]);
//# sourceMappingURL=robotplayer.js.map