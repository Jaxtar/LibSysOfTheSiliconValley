import { customElement, html, LitElement } from 'lit-element';

@customElement('main-page')
export class MainPage extends LitElement {
  createRenderRoot() {
    // Do not use a shadow root
    return this;
  }
  render() {
    return html`<div>Content placeholder</div>`;
  }
}
