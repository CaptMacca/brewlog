export class ConcurrentUpdateError extends Error {
  constructor(message: string) {
    super(message);
  }
}
