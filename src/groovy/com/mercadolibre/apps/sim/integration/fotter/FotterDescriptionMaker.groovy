package com.mercadolibre.apps.sim.integration.fotter

/**
 * Created with IntelliJ IDEA.
 * User: saleram
 * Date: 12/12/12
 * Time: 6:03 PM
 * To change this template use File | Settings | File Templates.
 */
class FotterDescriptionMaker {

  static String getDescriptionText(String htmlDescription, String plainDescription) {
    if (!htmlDescription || htmlDescription?.trim() == '') {
      return plainDescription
    }
    else {
      return htmlDescription.replace(/{{MAGENTO_DESCRIPTION}}/, plainDescription).toString()
    }
  }


  static void main(String[] args) {
    assert this.getDescriptionText(originalField, "A gentle pair of sandals") == expectedResult
  }

    def static  originalField =
      """
<div style="width: 500px; margin: 0 auto; text-align: center;">
\t<span>
\t\t<a href="http://www.fotter.com.ar/" target="_blank">
\t\t\t<img src="http://www.fotter.com.ar/skin/frontend/fotter/default/images/logo_navidad.png" width="440" />
\t\t</a>
\t</span>
\t<br /><br />
\t{{MAGENTO_DESCRIPTION}}
\t<br /><br />
\t<span>&copy; 2010-2013 <a href="http://www.fotter.com.ar/" target="_blank">Fotter</a> SRL of Argentina</span>
</div>
"""

    def static expectedResult =
      """
<div style="width: 500px; margin: 0 auto; text-align: center;">
\t<span>
\t\t<a href="http://www.fotter.com.ar/" target="_blank">
\t\t\t<img src="http://www.fotter.com.ar/skin/frontend/fotter/default/images/logo_navidad.png" width="440" />
\t\t</a>
\t</span>
\t<br /><br />
\tA gentle pair of sandals
\t<br /><br />
\t<span>&copy; 2010-2013 <a href="http://www.fotter.com.ar/" target="_blank">Fotter</a> SRL of Argentina</span>
</div>
"""

}


