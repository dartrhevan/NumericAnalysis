
function calc() {

    function getRowArray(row) {
        const res = [];

        for(let ent of row) {
            res.push(Number.parseFloat(ent.value));
        }

        return res;
    }

    function getSystem() {
        const res = [];
        for(let row of document.getElementsByClassName('row')) {
            res.push(getRowArray(row.getElementsByClassName('ent')));
        }
        console.log(res);
        return res;
    }


    function directMove(sys) { //прямой проход

        function validateAndCorrect(sys) {

            function swapRows(row1, row2) {
                for(let i = 0; i < row1.length; i++) {
                    const temp = row1[i];
                    row1[i] = row2[i];
                    row2[i] = temp;
                }
            }

            for(let j = 1; j < sys.length; j++) {
                const row = sys.find(e => e[j - 1] == 0);
                if(row !== sys[j])
                    swapRows(row, sys[j]);
            }
        }

        for(let i = 0; i < sys.length - 1; i++) {
            if(sys[i][i] === 0) continue;
            for(let j = i + 1; j < sys.length; j++) {
                const coef = sys[j][i] / sys[i][i];
                for(let k = 0; k < sys[j].length; k++)
                    sys[j][k] = (sys[j][k] - sys[i][k] * coef).toFixed(15);
            }
        }
        validateAndCorrect(sys);
        console.log(sys);
    }

    function reverseMove(sys) {//обратный проход т(можешь заняться этим)
        for(let i = sys.length - 1; i >= 0 ; i--) {
            sys[i][sys.length] /= sys[i][i];
            sys[i][i] = 1;
            for(let j = i - 1; j >= 0; j--) {
                const coef = sys[j][i]  / sys[i][i];
                for(let k = sys.length; k >=0 ; k--) {
                    sys[j][k] = (sys[j][k] - sys[i][k] * coef).toFixed(15);
                }
            }
        }
        console.log(sys);
    }

    function output(sys) {//вывод результата(этим тоже)
        let resString = 'Результат: (';
        for(let i = 0 ; i < sys.length; i++)
        {
            resString+= sys[i][sys.length];
            if(i !== sys.length - 1)
                resString+= ', ';
        }
        resString += ')';
        alert(resString);
    }

    function gausMethod(sys) {
        directMove(sys);
        reverseMove(sys);
    }

    function runMethod(sys) {
        const us = [];
        const vs = [];
        const xs = [];
        const getA = i => sys[i][i - 1];
        const getB = i => sys[i][i];
        const getC = i => sys[i][i + 1];
        const getD = i => sys[i][sys[i].length - 1];
        function calcCoefficients() {
            us[0] = -getA(0) / getB(0);
            vs[0] = getD(0) / getB(0);

            for(let i = 1; i < sys.length; i++) {
                us[i] = - getC(i) / (getA(i) * us[i - 1] + getB(i));
                vs[i] = (getD(i) - getA(i) * vs[i - 1]) / (getA(i) * us[i - 1] + getB(i));
            }
        }
        calcCoefficients();

    }

    const sys = getSystem();
    gausMethod(sys);
    output(sys);
}

export default calc;
