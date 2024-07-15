package kr.dogfoot.hwpxlib.writer.section_xml.ctrl;

import kr.dogfoot.hwpxlib.commonstrings.AttributeNames;
import kr.dogfoot.hwpxlib.commonstrings.ElementNames;
import kr.dogfoot.hwpxlib.object.common.HWPXObject;
import kr.dogfoot.hwpxlib.object.common.parameter.*;
import kr.dogfoot.hwpxlib.reader.util.ValueConvertor;
import kr.dogfoot.hwpxlib.writer.common.ElementWriter;
import kr.dogfoot.hwpxlib.writer.common.ElementWriterManager;
import kr.dogfoot.hwpxlib.writer.common.ElementWriterSort;

public class ParameterListCoreWriter extends ElementWriter {
    public ParameterListCoreWriter(ElementWriterManager elementWriterManager) {
        super(elementWriterManager);
    }

    @Override
    public ElementWriterSort sort() {
        return ElementWriterSort.ParameterListCore;
    }

    @Override
    public void write(HWPXObject object) {
        ParameterListCore parameterList = (ParameterListCore) object;
        switchList(parameterList.switchList());

        switch (parameterList._objectType()) {
            case hp_parameters:
                xsb().openElement(ElementNames.hp_parameters);
                break;
            case hp_listParam:
                xsb().openElement(ElementNames.hp_listParam);
                break;
            case hp_parameterset:
                xsb().openElement(ElementNames.hp_parameterset);
                break;
        }

        xsb()
                .elementWriter(this)
                .attribute(AttributeNames.cnt, parameterList.cnt())
                .attribute(AttributeNames.name, parameterList.name());

        int count = parameterList.countOfParam();
        for (int index = 0; index < count; index++) {
            param(parameterList.getParam(index));
        }

        xsb().closeElement();
        releaseMe();
    }

    private void param(Param param) {
        switch (param._objectType()) {
            case hp_integerParam:
                integerParam((IntegerParam) param);
                break;
            case hp_unsignedintegerParam:
                unsignedIntegerParam((UnsignedIntegerParam) param);
                break;
            case hp_stringParam:
                stringParam((StringParam) param);
                break;
            case hp_listParam:
                listParam((ListParam)param);
                break;
            case hp_booleanParam:
                booleanParam((BooleanParam) param);
                break;
        }
    }


    private void integerParam(IntegerParam integerParam) {
        xsb()
                .openElement(ElementNames.hp_integerParam)
                .attribute(AttributeNames.name, integerParam.name())
                .text(integerParam.value().toString())
                .closeElement();
    }


    private void unsignedIntegerParam(UnsignedIntegerParam unsignedIntegerParam) {
        xsb()
                .openElement(ElementNames.hp_unsignedintegerParam)
                .attribute(AttributeNames.name, unsignedIntegerParam.name())
                .text(unsignedIntegerParam.value().toString())
                .closeElement();
    }

    private void stringParam(StringParam stringParam) {
        xsb()
                .openElement(ElementNames.hp_stringParam)
                .attribute(AttributeNames.name, stringParam.name())
                .attribute(AttributeNames.xml_space, stringParam.xml_space())
                .text(stringParam.value())
                .closeElement();
    }

    private void booleanParam(BooleanParam booleanParam) {
        xsb()
                .openElement(ElementNames.hp_booleanParam)
                .attribute(AttributeNames.name, booleanParam.name())
                .text(ValueConvertor.toParameterValue(booleanParam.value()))
                .closeElement();
    }

    private void listParam(ListParam listParam) {
        xsb()
                .openElement(ElementNames.hp_listParam)
                .attribute(AttributeNames.cnt, listParam.cnt())
                .attribute(AttributeNames.name, listParam.name());

        int count = listParam.countOfParam();
        for (int index = 0; index < count; index++) {
            param(listParam.getParam(index));
        }

        xsb().closeElement();
    }

    @Override
    protected void childInSwitch(HWPXObject child) {
        param((Param) child);
    }
}
