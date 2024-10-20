export const copyLinkToClipboard = async (link: string) => {
  if (navigator.clipboard && navigator.clipboard.writeText) {
    await navigator.clipboard.writeText(link);
  } else {
    const textArea = document.createElement('textarea');
    textArea.value = link;
    textArea.style.position = 'fixed';
    textArea.style.opacity = '0';
    document.body.appendChild(textArea);
    textArea.select();
    textArea.setSelectionRange(0, textArea.value.length);

    document.body.removeChild(textArea);
    throw new Error('Clipboard API não suportada');
  }
};
