export const defaultTransition = { delay: 0, type: 'spring', stiffness: 100 };

export const bottomToTop = {
  hidden: { y: 20, opacity: 0 },
  visible: { y: 0, opacity: 1 },
};

export const leftToRight = {
  hidden: { x: -20, opacity: 0 },
  visible: { x: 0, opacity: 1 },
};

export const rightToLeft = {
  hidden: { x: 20, opacity: 0 },
  visible: { x: 0, opacity: 1 },
};
